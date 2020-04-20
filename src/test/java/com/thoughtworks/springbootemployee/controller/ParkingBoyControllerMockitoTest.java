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
public class ParkingBoyControllerMockitoTest {
    @Autowired
    private ParkingBoyController parkingBoyController;

    @MockBean
    private ParkingBoyRepository parkingBoyRepository;

    @MockBean
    private EmployeeRepository employeeRepository;

    private List<Employee> employeesList;
    private List<ParkingBoy> parkingBoysList;

    @Before
    public void setUp() throws Exception {
        RestAssuredMockMvc.standaloneSetup(parkingBoyController);
        employeesList = Arrays.asList(
                new Employee(1, "Hilary", 20, "female", 10000, null),
                new Employee(2, "Leo", 20, "male", 10000, null),
                new Employee(3, "Jay", 20, "male", 10000, null),
                new Employee(4, "Wesley", 20, "male", 10000, null),
                new Employee(5, "Andy", 20, "male", 10000, null)
        );

        Mockito.when(employeeRepository.findAll()).thenReturn(employeesList);

        parkingBoysList = Arrays.asList(
                new ParkingBoy(1, "Co2", 1),
                new ParkingBoy(2, "Dancing Leo", 2)
        );

        Mockito.when(parkingBoyRepository.findAll()).thenReturn(parkingBoysList);
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
        Mockito.when(parkingBoyRepository.findById(1)).thenReturn(Optional.of(parkingBoysList.get(0)));
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
    public void should_add_parking_boy_successfully(){
        ParkingBoy newParkingBoy = new ParkingBoy(3, "Jay Chou", 3);

        MockMvcResponse response = given().contentType(ContentType.JSON)
                .body(newParkingBoy)
                .when()
                .post("/parking-boys");

        Assert.assertEquals(201, response.getStatusCode());

        Mockito.verify(parkingBoyRepository, Mockito.times(1)).save(newParkingBoy);

    }

    @Test
    public void should_delete_parking_boy_successfully_when_insert_id(){
        ParkingBoy deleteParkingBoy = parkingBoysList.get(0);

        MockMvcResponse response = given().contentType(ContentType.JSON)
                .when()
                .delete("/parking-boys/1");
    }
}

