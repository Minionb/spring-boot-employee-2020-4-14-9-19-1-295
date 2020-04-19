package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.Employee;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;


@RunWith(SpringRunner.class)
@SpringBootTest
public class EmployeeControllerTest {
    @Autowired
    private EmployeeController employeeController;

    @Autowired
    EmployeeRepository employeeRepository;

    List<Employee> employeesList = new ArrayList<>();

    @Before
    public void setUp() throws Exception {
        RestAssuredMockMvc.standaloneSetup(employeeController);
//        employeesList = new ArrayList<>(Arrays.asList(
//                new Employee(1, "Hilary", 20, "female", 10000, 1),
//                new Employee(2, "Leo", 20, "male", 10000, 1),
//                new Employee(3, "Jay", 20, "male", 10000, 1),
//                new Employee(4, "Wesley", 20, "male", 10000, 2),
//                new Employee(5, "Andy", 20, "male", 10000, 2)));

        employeeRepository.deleteAll();
        employeeRepository.resetAutoIncrement();
        employeeRepository.save(new Employee(1, 20, null, "female", "Hilary", 10000));
        employeeRepository.save(new Employee(2, 20, null, "male", "Leo", 10000));
        employeeRepository.save(new Employee(3, 20, null, "male", "Jay", 10000));
        employeeRepository.save(new Employee(4,20 , null, "male", "Wesley", 10000));
        employeeRepository.save(new Employee(5, 20, null, "male", "Andy", 10000));
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
    public void should_find_employee_by_gender() {
        MockMvcResponse response = given().contentType(ContentType.JSON)
                .params("gender", "male")
                .when()
                .get("/employees");

        Assert.assertEquals(200, response.getStatusCode());

//        List<Employee> employees = response.getBody().as(new TypeRef<List<Employee>>() {
//            @Override
//            public Type getType() {
//                return super.getType();
//            }
//        });
//        Assert.assertEquals(2, employees.size());
//        Assert.assertEquals("Jay", employees.get(0).getName());
    }

    @Test
    public void should_add_employee_successful_when_create_new_employee() {
        Employee employee = new Employee(5, 20, null, "female", "Kathy", 10000);
        MockMvcResponse response = given().contentType(ContentType.JSON)
                .body(employee)
                .when()
                .post("/employees");

        Assert.assertEquals(201, response.getStatusCode());

//        Employee employeeResponse = response.getBody().as(Employee.class);
//
//        Assert.assertEquals("Kathy", employeeResponse.getName());
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

//        Assert.assertEquals(4, employees.size());
//        Assert.assertEquals("Hilary", employees.get(0).getName());
//        Assert.assertEquals("Jay", employees.get(1).getName());
//        Assert.assertEquals("Candy", employees.get(2).getName());
//        Assert.assertEquals("Tommy", employees.get(3).getName());
    }

    @Test
    public void should_delete_employees_successful_when_import_employee_id() {
        MockMvcResponse response = given().contentType(ContentType.JSON)
                .when()
                .delete("/employees/5");

        Assert.assertEquals(200, response.getStatusCode());
        // Assert.assertEquals(3, this.employeeRepository.findAll().size());

    }

    @Test
    public void should_update_employee_information_by_id() {
        Employee newEmployee = new Employee(3, 20, null, "female", "Kathy", 10000);
        MockMvcResponse response = given().contentType(ContentType.JSON)
                .body(newEmployee)
                .when()
                .put("/employees/3");

        Assert.assertEquals(200, response.getStatusCode());
//        Employee employee = response.getBody().as(Employee.class);
//        Assert.assertEquals(3, employee.getId().intValue());
//        Assert.assertEquals("Kathy", employee.getName());
    }

    @Test
    public void should_return_employees_in_page() {
        MockMvcResponse response = given().contentType(ContentType.JSON)
                .when()
                .get("/employees?page=2&pageSize=2");

        List<Employee> employees = response.getBody().as(new TypeRef<List<Employee>>() {
            @Override
            public Type getType() {
                return super.getType();
            }
        });
        Assert.assertEquals(200, response.getStatusCode());
//        Assert.assertEquals("Candy", employees.get(0).getName());
//        Assert.assertEquals("Tommy", employees.get(1).getName());
    }
}