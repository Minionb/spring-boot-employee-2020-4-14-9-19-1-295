package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.Employee;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
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

    @DeleteMapping("/{employeeId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> deleteEmployee(@PathVariable int employeeId){
        Employee targetEmployee = this.employees.stream().filter(employee -> employee.getId() == employeeId).findFirst().orElse(null);
        if (targetEmployee != null) {
            employees.remove(targetEmployee);
            return new ResponseEntity<String>((MultiValueMap<String, String>) targetEmployee, HttpStatus.OK);

        }
        return new ResponseEntity<>("Employee doesn't exist", HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/{employeeId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> updateEmployee(@PathVariable int employeeId, Employee newEmployee) {
        Employee targetEmployee = this.employees.stream().filter(employee -> employee.getId() == employeeId).findFirst().orElse(null);
        if (targetEmployee != null) {
            employees.set(employees.indexOf(targetEmployee), newEmployee);
            return new ResponseEntity<>(newEmployee, HttpStatus.OK);

        }
        return new ResponseEntity<>("Employee doesn't exist", HttpStatus.BAD_REQUEST);
    }


}
