package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.Employee;
import org.junit.Before;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeControllerTest {
    private EmployeeController employeeController;

    @Before
    public void setUp() throws Exception {
        employeeController = new EmployeeController();
    }
    @Test
    public void should_create_new_employee_Sandy(){
        Employee employee = new Employee(5,"Sandy",22,"Female");
        employeeController.createNewEmployee(employee);
    }

}