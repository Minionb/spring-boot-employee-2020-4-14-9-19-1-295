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

import javax.validation.constraints.Null;
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

    @Autowired
    private EmployeeRepository employeeRepository;

    @Before
    public void setUp() throws Exception {
        RestAssuredMockMvc.standaloneSetup(companyController);

        employeeRepository.deleteAll();
        employeeRepository.resetAutoIncrement();
        companyRepository.deleteAll();
        companyRepository.resetAutoIncrement();

        companyRepository.save(new Company(1, "Alibaba", 200, null));
        companyRepository.save(new Company(2, "Chocolate Factory", 50, null));


        employeeRepository.save(new Employee(1, "Hilary", 20, "female", 10000, 1));
        employeeRepository.save(new Employee(2, "Leo", 20, "male", 10000, 1));
        employeeRepository.save(new Employee(3, "Jay", 20, "male", 10000, 1));
    }

    @Test
    public void should_return_all_companies() {
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
    public void should_return_company_by_id() {
        MockMvcResponse response = given().contentType(ContentType.JSON)
                .when()
                .get("/companies/1");

        Assert.assertEquals(200, response.getStatusCode());

        Company company = response.getBody().as(Company.class);

        Assert.assertEquals(1, company.getId().intValue());
        Assert.assertEquals("Alibaba", company.getCompanyName());
    }

    @Test
    public void should_return_employees_of_a_company() {
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

        Assert.assertEquals(3, employee.size());
    }

    @Test
    public void should_return_2_companies_in_page_1() {
        MockMvcResponse response = given().contentType(ContentType.JSON)
                .when()
                .get("/companies?page=1&pageSize=2");

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
    public void should_add_company() {
        Company newCompany = new Company(3, "OOCL", 400, null);

        MockMvcResponse response = given().contentType(ContentType.JSON)
                .body(newCompany)
                .when()
                .post("/companies");

        Assert.assertEquals(201, response.getStatusCode());

        Company company = response.getBody().as(Company.class);

        Assert.assertEquals(3, this.companyRepository.findById(3).orElse(null).getId().intValue());
        Assert.assertEquals("OOCL", this.companyRepository.findById(3).orElse(null).getCompanyName());
        Assert.assertEquals(400, this.companyRepository.findById(3).orElse(null).getEmployeesNumber().intValue());
    }

    @Test
    public void should_update_company_by_id() {
        Assert.assertEquals("Chocolate Factory", this.companyRepository.findById(2).orElse(null).getCompanyName());
        Assert.assertEquals(50, this.companyRepository.findById(2).orElse(null).getEmployeesNumber().intValue());

        Company selectedCompany = new Company(2,"Minion Factory",300,null);

        MockMvcResponse response = given().contentType(ContentType.JSON)
                .body(selectedCompany)
                .when()
                .put("/companies/2");

        Assert.assertEquals(200, response.getStatusCode());

        Assert.assertEquals("Minion Factory", this.companyRepository.findById(2).orElse(null).getCompanyName());
        Assert.assertEquals(300, this.companyRepository.findById(2).orElse(null).getEmployeesNumber().intValue());
    }

    @Test
    public void should_delete_employees_of_the_company_by_id() {
        Assert.assertEquals(3, this.companyRepository.findById(1).orElse(null).getEmployees().size());
        MockMvcResponse response = given().contentType(ContentType.JSON)
                .when()
                .delete("/companies/1");

        Assert.assertEquals(200, response.getStatusCode());
        Assert.assertEquals(0, this.companyRepository.findById(1).orElse(null).getEmployees().size());
    }

}