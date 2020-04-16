package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService service = new EmployeeService();

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
        return service.update(employeeId, newEmployee);

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
        return service.getPage(page, pageSize);
    }

}
