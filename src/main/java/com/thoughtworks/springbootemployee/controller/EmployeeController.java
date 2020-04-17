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
        boolean isDelete = service.delete(employeeId);
        if (!isDelete) {
            return new ResponseEntity<>("Error, employee is not exist.", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Remove employee with id " + employeeId + " successfully", HttpStatus.OK);
    }

    @PutMapping("/{employeeId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> updateEmployee(@PathVariable int employeeId, @RequestBody Employee newEmployee) {
        boolean isUpdate = service.updateEmployees(employeeId, newEmployee);
        if (!isUpdate) {
            return new ResponseEntity<>("Error, employee is not exist.", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(newEmployee, HttpStatus.OK);
    }

    @GetMapping("/{employeeId}")
    public ResponseEntity<Object> getEmployeesById(@PathVariable int employeeId) {
        Employee targetEmployee = service.getById(employeeId);
        if (targetEmployee != null) {
            return new ResponseEntity<>(targetEmployee, HttpStatus.OK);
        }
        return new ResponseEntity<>("Employee does not exist", HttpStatus.BAD_REQUEST);
    }

    @GetMapping(params = {"gender"})
    public ResponseEntity<Object> getEmployeesByGender(@RequestParam(value = "gender") String gender) {
        List<Employee> returnEmployees = service.getEmployeeByGender(gender);
        return new ResponseEntity<>(returnEmployees, HttpStatus.OK);
    }

    @GetMapping(params = {"page", "pageSize"})
    public ResponseEntity<Object> getEmployeesPage(@RequestParam(value = "page") int page, @RequestParam(value = "pageSize") int pageSize) {
        List<Employee> returnEmployees = service.getEmployeesWithPagination(page, pageSize);
        return new ResponseEntity<>(returnEmployees, HttpStatus.OK);
    }

}
