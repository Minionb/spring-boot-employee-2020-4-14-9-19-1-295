package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.model.ParkingBoy;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import com.thoughtworks.springbootemployee.repository.ParkingBoyRepository;
import io.restassured.http.ContentType;
import io.restassured.mapper.TypeRef;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import io.restassured.module.mockmvc.response.MockMvcResponse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ParkingBoyControllerTest {
    @Autowired
    private ParkingBoyController parkingBoyController;

    @Autowired
    private ParkingBoyRepository parkingBoyRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    private List<Employee> employeesList;
    private List<ParkingBoy> parkingBoysList;

    @Before
    public void setUp() throws Exception {
        RestAssuredMockMvc.standaloneSetup(parkingBoyController);

        parkingBoyRepository.deleteAll();
        parkingBoyRepository.resetAutoIncrement();
        employeeRepository.deleteAll();
        employeeRepository.resetAutoIncrement();

        employeeRepository.save(new Employee(1, "Hilary", 20, "female", 10000, null));
        employeeRepository.save(new Employee(2, "Leo", 20, "male", 10000, null));
        employeeRepository.save(new Employee(3, "Jay", 20, "male", 10000, null));
        employeeRepository.save(new Employee(4, "Wesley", 20, "male", 10000, null));
        employeeRepository.save(new Employee(5, "Andy", 20, "male", 10000, null));

        parkingBoyRepository.save(new ParkingBoy(1, "Co2", 1));
        parkingBoyRepository.save(new ParkingBoy(2, "Dancing Leo", 2));


    }

    @Test
    public void should_return_all_parking_boys() {
        MockMvcResponse response = given().contentType(ContentType.JSON)
                .when()
                .get("/parking-boys");

        Assert.assertEquals(200, response.getStatusCode());

        List<ParkingBoy> parkingBoys = response.getBody().as(new TypeRef<List<ParkingBoy>>() {
            @Override
            public Type getType() {
                return super.getType();
            }
        });
        Assert.assertEquals(2, parkingBoys.size());
    }

    @Test
    public void should_return_parking_boy_when_insert_id() {
        MockMvcResponse response = given().contentType(ContentType.JSON)
                .when()
                .get("/parking-boys/1");

        Assert.assertEquals(200, response.getStatusCode());

        ParkingBoy parkingBoy = response.getBody().as(ParkingBoy.class);

        Assert.assertEquals(1, parkingBoy.getId().intValue());
        Assert.assertEquals("Co2", parkingBoy.getNickname());
        Assert.assertEquals(1, parkingBoy.getEmployeeId().intValue());
    }

    @Test
    public void should_add_parking_boy_successfully() {
        ParkingBoy newParkingBoy = new ParkingBoy(3, "Jay Chou", 3);
        Assert.assertEquals(2,this.parkingBoyRepository.findAll().size());

        MockMvcResponse response = given().contentType(ContentType.JSON)
                .body(newParkingBoy)
                .when()
                .post("/parking-boys");

        Assert.assertEquals(201, response.getStatusCode());

        ParkingBoy parkingBoy = response.getBody().as(ParkingBoy.class);
        Assert.assertEquals(3,this.parkingBoyRepository.findAll().size());
        Assert.assertEquals(3,parkingBoy.getId().intValue());
        Assert.assertEquals("Jay Chou",parkingBoy.getNickname());
        Assert.assertEquals(3,parkingBoy.getEmployeeId().intValue());

    }

    @Test
    public void should_delete_parking_boy_successfully_when_insert_id() {
        Assert.assertEquals(2,this.parkingBoyRepository.findAll().size());

        MockMvcResponse response = given().contentType(ContentType.JSON)
                .when()
                .delete("/parking-boys/1");

        Assert.assertEquals(200, response.getStatusCode());

        Assert.assertEquals(1,this.parkingBoyRepository.findAll().size());
    }

    @Test
    public void should_update_employee_information_successfully_when_insert_id(){
        ParkingBoy updateParkingBoy = new ParkingBoy(1, "Minion", 1);

        MockMvcResponse response = given().contentType(ContentType.JSON)
                .body(updateParkingBoy)
                .when()
                .put("/parking-boys/1");

        Assert.assertEquals(200, response.getStatusCode());

        ParkingBoy parkingBoy = response.getBody().as(ParkingBoy.class);

        Assert.assertEquals("Minion",parkingBoy.getNickname());
    }

    @Test
    public void should_return_2_parking_boy_in_page_1(){
        MockMvcResponse response = given().contentType(ContentType.JSON)
                .when()
                .get("/parking-boys?page=1&pageSize=2");

        Assert.assertEquals(200, response.getStatusCode());
        List<ParkingBoy> parkingBoys = response.getBody().as(new TypeRef<List<ParkingBoy>>() {
            @Override
            public Type getType() {
                return super.getType();
            }
        });

        Assert.assertEquals(2,parkingBoys.size());
        Assert.assertEquals("Co2",parkingBoys.get(0).getNickname());
        Assert.assertEquals("Dancing Leo",parkingBoys.get(1).getNickname());
    }

    @Test
    public void should_return_parking_boy_when_query_nickname(){
        MockMvcResponse response = given().contentType(ContentType.JSON)
                .params("nickname", "Co2")
                .when()
                .get("/parking-boys");

        Assert.assertEquals(200, response.getStatusCode());

        List<ParkingBoy> parkingBoys = response.getBody().as(new TypeRef<List<ParkingBoy>>() {
            @Override
            public Type getType() {
                return super.getType();
            }
        });

        Assert.assertEquals(1, parkingBoys.size());
        Assert.assertEquals("Co2", parkingBoys.get(0).getNickname());
    }
}

