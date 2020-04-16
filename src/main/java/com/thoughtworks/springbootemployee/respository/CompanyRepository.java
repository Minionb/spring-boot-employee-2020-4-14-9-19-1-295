package com.thoughtworks.springbootemployee.respository;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
@Repository
public class CompanyRepository {
    private List<Company> companies = new ArrayList<>();

    public CompanyRepository(List<Company> companies){
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee(1, "Hilary", 23, "female", 10000));
        employees.add(new Employee(2, "Jay", 30, "male", 10000));
        employees.add(new Employee(3, "Candy", 23, "female", 10000));
        employees.add(new Employee(4, "Tommy", 26, "male", 10000));

        companies.add(new Company(1, "Alibaba", 200, employees.subList(0, 2)));
        companies.add(new Company(2, "Chocolate Factory", 50, employees.subList(2, 4)));
        this.companies = companies;
    }
    public List<Company> findAll() {
        return this.companies;
    }

    public ResponseEntity<Object> findById(int companyId) {
        Company selectedCompany = this.companies.stream().filter(company -> company.getId() == companyId).findFirst().orElse(null);
        if (selectedCompany != null) {
            return new ResponseEntity<>(selectedCompany, HttpStatus.OK);
        }
        return new ResponseEntity<>("Company doesn't exist", HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<Object> findEmployeesById(int companyId) {
        Company selectedCompany = this.companies.stream().filter(company -> company.getId() == companyId).findFirst().orElse(null);
        if (selectedCompany != null) {
            return new ResponseEntity<>(selectedCompany.getEmployees(), HttpStatus.OK);
        }
        return new ResponseEntity<>("Company doesn't exist", HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<Object> findCompaniesInPage(int page, int pageSize) {
        int startingIndex = (page - 1) * pageSize;
        int endingIndex = page * pageSize;
        if (this.companies.size() < startingIndex) {
            return new ResponseEntity<>("This page doesn't exists as companies list size is not big enough, please go back to page 1", HttpStatus.OK);
        } else if (this.companies.size() > startingIndex && this.companies.size() < endingIndex) {
            return new ResponseEntity<>(this.companies.subList(startingIndex, companies.size()), HttpStatus.OK);
        }
        return new ResponseEntity<>(this.companies.subList(startingIndex, endingIndex), HttpStatus.OK);
    }

    public Company create(Company company) {
        companies.add(company);
        return company;
    }

    public ResponseEntity<Object> update(int companyId, Company newCompany) {
        Company selectedCompany = this.companies.stream().filter(company -> company.getId() == companyId).findFirst().orElse(null);
        if (selectedCompany != null) {
            companies.set(companies.indexOf(selectedCompany), newCompany);
            return new ResponseEntity<>(newCompany, HttpStatus.OK);
        }
        return new ResponseEntity<>("Company doesn't exist", HttpStatus.BAD_REQUEST);
    }


    public ResponseEntity<Object> delete(int companyID) {
        Company selectedCompany = this.companies.stream().filter(company -> company.getId() == companyID).findFirst().orElse(null);
        if (selectedCompany != null && selectedCompany.getEmployees() != null) {
            selectedCompany.setEmployees(new ArrayList<>());
            return new ResponseEntity<>(selectedCompany, HttpStatus.OK);
        }
        return new ResponseEntity<>("Company doesn't exist", HttpStatus.BAD_REQUEST);
    }
}
