package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
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
public class CompanyControllerTest {
    @Autowired
    private CompanyController companyController;

    @Autowired
    private CompanyRepository companyRepository;

    @Before
    public void setUp() throws Exception{
        RestAssuredMockMvc.standaloneSetup(companyController);
        List<Employee> employees=  new ArrayList<>(Arrays.asList(
                new Employee(1, "Hilary", 23, "female", 10000),
                new Employee(2, "Jay", 30, "male", 10000),
                new Employee(3, "Candy", 23, "female", 10000),
                new Employee(4, "Tommy", 26, "male", 10000)
        ));

        companyRepository.setCompanies(new ArrayList<>(Arrays.asList(
                new Company(1, "Alibaba", 200, employees.subList(0, 2)),
                new Company(2, "Chocolate Factory", 50, employees.subList(2, 4))
        )));
    }

    @Test
    public void should_return_all_companies(){
        MockMvcResponse response = given().contentType(ContentType.JSON)
                .when()
                .get("/companies");

        Assert.assertEquals(200, response.getStatusCode());

        List<Company> companies = response.getBody().as(new TypeRef<List<Company>>() {
            @Override
            public Type getType() {
                return super.getType();
            }
        });

        Assert.assertEquals(2, companies.size());
        Assert.assertEquals("Alibaba", companies.get(0).getCompanyName());
        Assert.assertEquals("Chocolate Factory", companies.get(1).getCompanyName());
    }

    @Test
    public void should_return_company_by_id(){
        MockMvcResponse response = given().contentType(ContentType.JSON)
                .when()
                .get("/companies/1");

        Assert.assertEquals(200, response.getStatusCode());

        Company company = response.getBody().as(Company.class);

        Assert.assertEquals(1, company.getId());
        Assert.assertEquals("Alibaba", company.getCompanyName());
    }

    @Test
    public void should_return_employees_of_a_company(){
        MockMvcResponse response = given().contentType(ContentType.JSON)
                .when()
                .get("/companies/1/employees");

        Assert.assertEquals(200, response.getStatusCode());

        List<Employee> employee = response.getBody().as(new TypeRef<List<Employee>>() {
            @Override
            public Type getType() {
                return super.getType();
            }
        });


        Assert.assertEquals(1, employee.get(0).getId().intValue());
        Assert.assertEquals(2, employee.get(1).getId().intValue());
        Assert.assertEquals("Hilary", employee.get(0).getName());
        Assert.assertEquals("Jay", employee.get(1).getName());
    }

    @Test
    public void should_return_companies_in_page(){
        MockMvcResponse response = given().contentType(ContentType.JSON)
                .when()
                .get("/companies?page=1&pageSize=5");

        Assert.assertEquals(200, response.getStatusCode());

        List<Company> companies = response.getBody().as(new TypeRef<List<Company>>() {
            @Override
            public Type getType() {
                return super.getType();
            }
        });

        Assert.assertEquals("Alibaba", companies.get(0).getCompanyName());
        Assert.assertEquals("Chocolate Factory", companies.get(1).getCompanyName());

    }

    @Test
    public void should_add_company(){
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee(5,"Leo",22,"male",10000));
        employees.add(new Employee(6,"Wesley",20,"male",10000));
        employees.add(new Employee(7,"Andy",20,"male",10000));
        Company newCompany = new Company(3, "OOCL", 400, employees);

        MockMvcResponse response = given().contentType(ContentType.JSON)
                .body(newCompany)
                .when()
                .post("/companies");

        Assert.assertEquals(201, response.getStatusCode());

        Company company = response.getBody().as(Company.class);

        Assert.assertEquals(3, company.getId());
        Assert.assertEquals("OOCL", company.getCompanyName());
        Assert.assertEquals(400, company.getEmployeesNumber());
        Assert.assertEquals(3, company.getEmployees().size());

    }

    @Test
    public void should_update_company_by_id(){
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee(5,"Leo",22,"male",10000));
        employees.add(new Employee(6,"Wesley",20,"male",10000));
        employees.add(new Employee(7,"Andy",20,"male",10000));
        Company selectedCompany = new Company(1,"Minion Factory",300,employees);

        MockMvcResponse response = given().contentType(ContentType.JSON)
                .body(selectedCompany)
                .when()
                .put("/companies/1");

        Assert.assertEquals(200, response.getStatusCode());

        Company company = response.getBody().as(Company.class);

        Assert.assertEquals(1, company.getId());
        Assert.assertEquals("Minion Factory", company.getCompanyName());
        Assert.assertEquals(300, company.getEmployeesNumber());
        Assert.assertEquals(3, company.getEmployees().size());
    }

    @Test
    public void should_delete_employees_of_the_company_by_id(){
        MockMvcResponse response = given().contentType(ContentType.JSON)
                .when()
                .delete("/companies/1");

        Assert.assertEquals(200, response.getStatusCode());
    }

}