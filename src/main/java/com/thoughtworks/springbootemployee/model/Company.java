package com.thoughtworks.springbootemployee.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "company")
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String companyName;
    private Integer employeesNumber;

    @OneToMany(targetEntity = Employee.class, mappedBy = "companyId", fetch = FetchType.EAGER)
    private List<Employee> employees;
    //private List<Employee> employees;

//    public Company(int companyId, String companyName, int employeesNumber, List<Employee> employees) {
//        this.companyId = companyId;
//        this.companyName = companyName;
//        this.employeesNumber = employeesNumber;
//        this.employees = employees;
//    }
//
//    public Company() {
//
//    }
//
//    public int getId() {
//        return companyId;
//    }
//
//    public void setId(int id) {
//        this.companyId = id;
//    }
//
//    public String getCompanyName() {
//        return companyName;
//    }
//
//    public void setCompanyName(String companyName) {
//        this.companyName = companyName;
//    }
//
//    public int getEmployeesNumber() {
//        return employeesNumber;
//    }
//
//    public void setEmployeesNumber(int employeesNumber) {
//        this.employeesNumber = employeesNumber;
//    }
//
//    public List<Employee> getEmployees() {
//        return employees;
//    }
//
//    public void setEmployees(List<Employee> employees) {
//        this.employees = employees;
//    }
}