package com.thoughtworks.springbootemployee.controller;

import static org.junit.jupiter.api.Assertions.*;

import com.thoughtworks.springbootemployee.model.Employee;
import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import io.restassured.module.mockmvc.response.MockMvcResponse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import io.restassured.mapper.TypeRef;
import java.lang.reflect.Type;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;


@RunWith(SpringRunner.class)
@SpringBootTest
public class EmployeeControllerTest {
    @Autowired
    private EmployeeController employeeController;
    private List<Employee> employees = new ArrayList<>();

    @Before
    public void setUp() throws Exception{
        RestAssuredMockMvc.standaloneSetup(employeeController);
        employees.add(new Employee(1, "Hilary", 23, "female", 10000));
        employees.add(new Employee(2, "Jay", 30, "male", 10000));
        employees.add(new Employee(3, "Candy", 23, "female", 10000));
        employees.add(new Employee(4, "Tommy", 26, "male", 10000));
        employeeController.setEmployees(employees);
    }


    @Test
    public void should_find_employ_by_id(){
        MockMvcResponse response = given().contentType(ContentType.JSON)
                .when()
                .get("/employees/1");

        Assert.assertEquals(200, response.getStatusCode());

        Employee employee = response.getBody().as(Employee.class);
        Assert.assertEquals(1, employee.getId());
        Assert.assertEquals("Hilary", employee.getName());
    }

    @Test
    public void should_find_employee_by_gender() {
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
        Assert.assertEquals(2, employees.size());
        Assert.assertEquals("Jay", employees.get(0).getName());
    }

    @Test
    public void should_add_employee() {
        Employee employee = new Employee(5,"Kathy",26,"female",10000);
        MockMvcResponse response = given().contentType(ContentType.JSON)
                .body(employee)
                .when()
                .post("/employees");

        Assert.assertEquals(201, response.getStatusCode());

        Employee employeeResponse = response.getBody().as(Employee.class);

        Assert.assertEquals("Kathy", employeeResponse.getName());
    }

    @Test
    public void should_return_all_employees(){
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

        Assert.assertEquals(4, employees.size());
        Assert.assertEquals("Hilary", employees.get(0).getName());
        Assert.assertEquals("Jay", employees.get(1).getName());
        Assert.assertEquals("Candy", employees.get(2).getName());
        Assert.assertEquals("Tommy", employees.get(3).getName());
    }

    @Test
    public void should_delete_employees_by_id(){
        MockMvcResponse response = given().contentType(ContentType.JSON)
                .when()
                .delete("/employees/1");

        Assert.assertEquals(200, response.getStatusCode());

        Employee employee = response.getBody().as(Employee.class);
        Assert.assertEquals(1, employee.getId());
        Assert.assertEquals("Hilary", employee.getName());

    }

    @Test
    public void should_update_employee_information_by_id(){
        Employee newEmployee = new Employee(1,"Kathy",26,"female",10000);
        MockMvcResponse response = given().contentType(ContentType.JSON)
                .body(newEmployee)
                .when()
                .put("/employees/1");

        Assert.assertEquals(200, response.getStatusCode());
        Employee employee = response.getBody().as(Employee.class);
        Assert.assertEquals(1, employee.getId());
        Assert.assertEquals("Kathy", employee.getName());
    }
}