package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.respository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository repository;

    public List<Employee> getAll() {
        return repository.findAll();
    }

    public Employee getById(int employeeId) {
        return repository.findEmployeeById(employeeId);
    }

    public Employee create(Employee employee) {
        return repository.save(employee);
    }

    public boolean delete(int employeeId) {
        Employee targetEmployee = repository.findEmployeeById((employeeId));
        if (targetEmployee == null){
            return false;
        }
        repository.deleteEmployeeById(targetEmployee);
        return true;
    }

    public boolean updateEmployees(int employeeId, Employee newEmployee) {
        Employee targetEmployee = repository.findEmployeeById(employeeId);
        if (targetEmployee == null) {
            return false;
        }
        repository.updateById(employeeId, newEmployee);
        return true;
    }

    public List<Employee> getEmployeeByGender(String gender) {
        return repository.findByGender(gender);
    }

    public List<Employee> getEmployeesWithPagination(int page, int pageSize) {
        int startingIndex = (page - 1) * pageSize;
        int endingIndex = page * pageSize;
        List<Employee> employees = this.repository.findAll();
        return employees.stream()
                .sorted(Comparator.comparing(Employee::getId))
                .skip(startingIndex)
                .limit(endingIndex)
                .collect(Collectors.toList());
    }
}
