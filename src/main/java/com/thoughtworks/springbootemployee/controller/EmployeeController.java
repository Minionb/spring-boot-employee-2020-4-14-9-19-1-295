package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.Employee;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    private List<Employee> employees = new ArrayList<>();

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Employee> getAllEmployee(){
        employees.add(new Employee(1,"Hilary",23,"Female"));
        employees.add(new Employee(2,"Jay",30,"Male"));
        employees.add(new Employee(3,"Candy",23,"Female"));
        employees.add(new Employee(4,"Tommy",26,"Male"));
        return employees;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Employee createNewEmployee(@RequestBody Employee employee){
        employees.add(employee);
        return employee;
    }




}
