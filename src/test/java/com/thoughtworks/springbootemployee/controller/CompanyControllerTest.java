package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.Company;
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
import org.springframework.test.context.junit4.SpringRunner;
import io.restassured.mapper.TypeRef;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CompanyControllerTest {
    @Autowired
    private CompanyController companyController;
    private List<Company> companies = new ArrayList<>();
    private List<Employee> employees = new ArrayList<>();

    @Before
    public void setUp() throws Exception{
        RestAssuredMockMvc.standaloneSetup(companyController);
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

}