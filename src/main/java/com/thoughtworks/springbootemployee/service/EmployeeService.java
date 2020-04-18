package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    public List<Employee> getAll() {
        return employeeRepository.findAll();
    }

    public Employee get(Integer employeeId) {
        return employeeRepository.findById(employeeId).orElse(null);
    }

    public Employee create(Employee employee) {
        return employeeRepository.save(employee);
    }

    public boolean delete(int employeeId) {
        Employee targetEmployee = employeeRepository.findById((employeeId)).orElse(null);
        if (targetEmployee == null){
            return false;
        }
        employeeRepository.delete(targetEmployee);
        return true;
    }

    public boolean updateEmployees(int employeeId, Employee newEmployee) {
        Employee targetEmployee = employeeRepository.findById(employeeId).orElse(null);

        if (targetEmployee == null) {
            return false;
        }

        if (newEmployee.getName() != null){
            targetEmployee.setName(newEmployee.getName());
        }

        if(newEmployee.getAge() != null){
            targetEmployee.setAge(newEmployee.getAge());
        }

        if(newEmployee.getGender() != null){
            targetEmployee.setGender(newEmployee.getGender());
        }

        if(newEmployee.getSalary() != null){
            targetEmployee.setSalary(newEmployee.getSalary());
        }
        employeeRepository.save(targetEmployee);
        return true;
    }

    public List<Employee> getEmployeeByGender(String gender) {
        return employeeRepository.findAllByGender(gender);
    }

    public List<Employee> getEmployeesWithPagination(Integer page, Integer pageSize) {
        return employeeRepository.findAll(PageRequest.of(page-1, pageSize)).getContent();
    }
}
