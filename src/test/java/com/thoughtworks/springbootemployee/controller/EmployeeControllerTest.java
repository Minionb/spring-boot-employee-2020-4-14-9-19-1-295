package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import io.restassured.module.mockmvc.response.MockMvcResponse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import io.restassured.mapper.TypeRef;

import java.lang.reflect.Type;
import java.util.List;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;


@RunWith(SpringRunner.class)
@SpringBootTest
public class EmployeeControllerTest {
    @Autowired
    private EmployeeController employeeController;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Before
    public void setUp() throws Exception {
        RestAssuredMockMvc.standaloneSetup(employeeController);

        employeeRepository.deleteAll();
        employeeRepository.resetAutoIncrement();
        employeeRepository.save(new Employee(1, "Hilary", 20, "female", 10000, null));
        employeeRepository.save(new Employee(2, "Leo", 20, "male", 10000, null));
        employeeRepository.save(new Employee(3, "Jay", 20, "male", 10000, null));
        employeeRepository.save(new Employee(4, "Wesley", 20, "male", 10000, null));
        employeeRepository.save(new Employee(5, "Andy", 20, "male", 10000, null));
    }


    @Test
    public void should_return_employee_when_insert_id() {
        MockMvcResponse response = given().contentType(ContentType.JSON)
                .when()
                .get("/employees/1");

        Assert.assertEquals(200, response.getStatusCode());

        Employee employee = response.getBody().as(Employee.class);
        Assert.assertEquals(1, employee.getId().intValue());
        Assert.assertEquals("Hilary", employee.getName());
    }

    @Test
    public void should_return_employee_list_when_query_gender() {
        MockMvcResponse response = given().contentType(ContentType.JSON)
                .params("gender", "male")
                .when()
                .get("/employees");

        Assert.assertEquals(200, response.getStatusCode());

        List<Employee> employees = response.getBody().as(new TypeRef<List<Employee>>() {
            @Override
            public Type getType() {
                return super.getType();
            }
        });
        Assert.assertEquals(4, employees.size());
        Assert.assertEquals("Leo", employees.get(0).getName());
    }

    @Test
    public void should_add_employee_successfully_when_create_new_employee() {
        Assert.assertEquals(5, this.employeeRepository.findAll().size());

        Employee employee = new Employee(6, "Kathy", 26, "female", 10000, null);
        MockMvcResponse response = given().contentType(ContentType.JSON)
                .body(employee)
                .when()
                .post("/employees");

        Assert.assertEquals(201, response.getStatusCode());

        Employee employeeResponse = response.getBody().as(Employee.class);
        Assert.assertEquals(6, this.employeeRepository.findAll().size());
        Assert.assertEquals("Kathy", this.employeeRepository.findById(6).orElse(null).getName());
    }

    @Test
    public void should_return_all_employees() {
        MockMvcResponse response = given().contentType(ContentType.JSON)
                .when()
                .get("/employees");

        Assert.assertEquals(200, response.getStatusCode());

        List<Employee> employees = response.getBody().as(new TypeRef<List<Employee>>() {
            @Override
            public Type getType() {
                return super.getType();
            }
        });

        Assert.assertEquals(5, employees.size());
        Assert.assertEquals("Hilary", employees.get(0).getName());
        Assert.assertEquals("Leo", employees.get(1).getName());
        Assert.assertEquals("Jay", employees.get(2).getName());
        Assert.assertEquals("Wesley", employees.get(3).getName());
        Assert.assertEquals("Andy", employees.get(4).getName());
    }

    @Test
    public void should_delete_employees_successful_when_import_employee_id() {
        Assert.assertEquals(5, this.employeeRepository.findAll().size());

        MockMvcResponse response = given().contentType(ContentType.JSON)
                .when()
                .delete("/employees/5");

        Assert.assertEquals(200, response.getStatusCode());
        Assert.assertEquals(4, this.employeeRepository.findAll().size());

    }

    @Test
    public void should_update_employee_information_successfully_when_insert_id() {
        companyRepository.deleteAll();
        companyRepository.resetAutoIncrement();
        companyRepository.save(new Company(1, "Alibaba", 200, null));

        Assert.assertEquals("Jay", this.employeeRepository.findById(3).orElse(null).getName());
        Assert.assertNull( this.employeeRepository.findById(3).orElse(null).getCompanyId());
        Assert.assertEquals(0, companyRepository.findById(1).orElse(null).getEmployees().size());

        Employee newEmployee = new Employee(3, "Kathy", 26, "female", 10000, 1);
        MockMvcResponse response = given().contentType(ContentType.JSON)
                .body(newEmployee)
                .when()
                .put("/employees/3");

        Assert.assertEquals(200, response.getStatusCode());

        Assert.assertEquals("Kathy", this.employeeRepository.findById(3).orElse(null).getName());
        Assert.assertEquals(1, this.employeeRepository.findById(3).orElse(null).getCompanyId().intValue());
        Assert.assertEquals(1, companyRepository.findById(1).orElse(null).getEmployees().size());
    }

    @Test
    public void should_return_2_employees_in_page_2() {
        MockMvcResponse response = given().contentType(ContentType.JSON)
                .when()
                .get("/employees?page=2&pageSize=2");

        Assert.assertEquals(200, response.getStatusCode());

        List<Employee> employees = response.getBody().as(new TypeRef<List<Employee>>() {
            @Override
            public Type getType() {
                return super.getType();
            }
        });

        Assert.assertEquals("Jay", employees.get(0).getName());
        Assert.assertEquals("Wesley", employees.get(1).getName());
    }
}