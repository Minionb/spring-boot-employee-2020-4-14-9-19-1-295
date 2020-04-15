package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.Employee;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    private List<Employee> employees = new ArrayList<>();

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Employee> getAllEmployee(){
        employees.add(new Employee(1,"Hilary",23,"female"));
        employees.add(new Employee(2,"Jay",30,"male"));
        employees.add(new Employee(3,"Candy",23,"female"));
        employees.add(new Employee(4,"Tommy",26,"male"));
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
    public ResponseEntity<Object> deleteEmployee(@PathVariable int employeeId){
        Employee targetEmployee = this.employees.stream().filter(employee -> employee.getId() == employeeId).findFirst().orElse(null);
        if (targetEmployee != null) {
            employees.remove(targetEmployee);
            return new ResponseEntity<>(targetEmployee, HttpStatus.OK);

        }
        return new ResponseEntity<>("Employee doesn't exist", HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/{employeeId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> updateEmployee(@PathVariable int employeeId, @RequestBody Employee newEmployee) {
        Employee targetEmployee = this.employees.stream().filter(employee -> employee.getId() == employeeId).findFirst().orElse(null);
        if (targetEmployee != null) {
            employees.set(employees.indexOf(targetEmployee), newEmployee);
            return new ResponseEntity<>(newEmployee, HttpStatus.OK);

        }
        return new ResponseEntity<>("Employee doesn't exist", HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/{employeeId}")
    public ResponseEntity<Object> getEmployeesById(@PathVariable int employeeID) {
        Employee targetEmployee = this.employees.stream().filter(employee -> employee.getId() == employeeID).findFirst().orElse(null);
        if (targetEmployee != null) {
            return new ResponseEntity<>(targetEmployee, HttpStatus.OK);
        }
        return new ResponseEntity<>("Employee does not exist", HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(method = RequestMethod.GET, params = {"gender"})
    public ResponseEntity<Object> getEmployeesByGender(@RequestParam(value = "gender") String gender) {
        List<Employee> returnEmployees = this.employees.stream().filter(employee -> employee.getGender().equals(gender)).collect(Collectors.toList());
        return new ResponseEntity<>(returnEmployees, HttpStatus.OK);
}


}
