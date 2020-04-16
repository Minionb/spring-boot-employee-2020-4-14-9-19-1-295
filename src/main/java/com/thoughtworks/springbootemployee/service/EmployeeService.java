package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.respository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository repository;

    public List<Employee> getAll() {
        return repository.findAll();
    }

    public ResponseEntity<Object> getById(int employeeId) {
        return repository.findById(employeeId);
    }

    public Employee create(Employee employee) {
        return repository.save(employee);
    }

    public ResponseEntity<Object> delete(int employeeId) {
        return repository.deleteById(employeeId);
    }

    public ResponseEntity<Object> update(int employeeId, Employee newEmployee) {
        return repository.updateById(employeeId, newEmployee);
    }

    public ResponseEntity<Object> getByGender(String gender) {
        return repository.findByGender(gender);
    }

    public ResponseEntity<Object> getPage(int page, int pageSize) {
        return repository.findPage(page, pageSize);
    }
}
