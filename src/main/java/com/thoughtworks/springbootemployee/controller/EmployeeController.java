package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    private List<Employee> employees = new ArrayList<>();

    @Autowired
    private EmployeeService service = new EmployeeService();

    public EmployeeController(){
        this.employees.add(new Employee(1, "Hilary", 23, "female", 10000));
        this.employees.add(new Employee(2, "Jay", 30, "male", 10000));
        this.employees.add(new Employee(3, "Candy", 23, "female", 10000));
        this.employees.add(new Employee(4, "Tommy", 26, "male", 10000));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Employee> getAllEmployee() {
        return service.getAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Employee createNewEmployee(@RequestBody Employee employee) {
        return service.create(employee);
    }

    @DeleteMapping("/{employeeId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> deleteEmployee(@PathVariable int employeeId) {
        return service.delete(employeeId);
    }

    @PutMapping("/{employeeId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> updateEmployee(@PathVariable int employeeId, @RequestBody Employee newEmployee) {
        return service.update(employeeId,newEmployee);

    }

    @GetMapping("/{employeeId}")
    public ResponseEntity<Object> getEmployeesById(@PathVariable int employeeId) {
        return service.getById(employeeId);
    }

    @GetMapping(params = {"gender"})
    public ResponseEntity<Object> getEmployeesByGender(@RequestParam(value = "gender") String gender) {
        return service.getByGender(gender);
    }

    @GetMapping(params = {"page", "pageSize"})
    public ResponseEntity<Object> getEmployeesPage(@RequestParam(value = "page") int page, @RequestParam(value = "pageSize") int pageSize) {
        return service.getPage(page,pageSize);
    }

}
