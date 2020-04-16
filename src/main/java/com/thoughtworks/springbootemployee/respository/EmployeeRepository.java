package com.thoughtworks.springbootemployee.respository;

import com.thoughtworks.springbootemployee.model.Employee;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class EmployeeRepository {
    private List<Employee> employees = new ArrayList<>();

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

    public ResponseEntity<Object> findById(int employeeId) {
        Employee targetEmployee = this.employees.stream().filter(employee -> employee.getId() == employeeId).findFirst().orElse(null);
        if (targetEmployee != null) {
            return new ResponseEntity<>(targetEmployee, HttpStatus.OK);
        }
        return new ResponseEntity<>("Employee does not exist", HttpStatus.BAD_REQUEST);
    }

    public Employee save(Employee employee) {
        employees.add(employee);
        return employee;
    }

    public ResponseEntity<Object> delete(int employeeId) {
        Employee targetEmployee = this.employees.stream()
                .filter(employee -> employee.getId() == employeeId)
                .findFirst()
                .orElse(null);

        if (targetEmployee != null) {
            employees.remove(targetEmployee);
            return new ResponseEntity<>(targetEmployee, HttpStatus.OK);

        }
        return new ResponseEntity<>("Employee doesn't exist", HttpStatus.BAD_REQUEST);
    }
}
