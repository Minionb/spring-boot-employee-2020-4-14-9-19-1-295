package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.respository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Scanner;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository repository;

    public List<Employee> getAll() {
        return repository.findAll();
    }

    public ResponseEntity<Object> getEmployeeById(int employeeId) {
        return repository.findById(employeeId);

    }

    public Employee create(Employee employee) {
        return repository.save(employee);
    }

    public ResponseEntity<Object> delete(int employeeId) {
        return repository.delete(employeeId);
    }
}
