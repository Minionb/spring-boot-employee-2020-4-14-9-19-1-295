package com.thoughtworks.springbootemployee.respository;

import com.thoughtworks.springbootemployee.model.Employee;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class EmployeeRepository {
    private List<Employee> employees = new ArrayList<>();

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public EmployeeRepository(List<Employee> employees) {
        employees.add(new Employee(1, "Hilary", 23, "female", 10000));
        employees.add(new Employee(2, "Jay", 30, "male", 10000));
        employees.add(new Employee(3, "Candy", 23, "female", 10000));
        employees.add(new Employee(4, "Tommy", 26, "male", 10000));
        this.employees = employees;
    }

    public List<Employee> findAll() {
        return employees;
    }

    public Employee findEmployeeById(int employeeId) {
        Employee targetEmployee = this.employees.stream().
                filter(employee -> employee.getId() == employeeId).findFirst().
                orElse(null);
        return targetEmployee;
    }

    public Employee save(Employee employee) {
        employees.add(employee);
        return employee;
    }

    public void deleteEmployeeById(Employee targetEmployee) {
        this.employees.remove(targetEmployee);
    }

    public void updateById(int employeeId, Employee newEmployee) {
        this.employees.set(employeeId,newEmployee);
    }

    public List<Employee> findByGender(String gender) {
        return this.employees.stream().
                filter(employee -> employee.getGender().equals(gender)).
                collect(Collectors.toList());
    }

}
