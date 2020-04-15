package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/companies")
public class CompanyController {
    private List<Company> companies = new ArrayList<>();
    private List<Employee> employees = new ArrayList<>();

    @GetMapping
    public List<Company> getAllCompanies() {
        employees.add(new Employee(1, "Hilary", 23, "female", 10000));
        employees.add(new Employee(2, "Jay", 30, "male", 10000));
        employees.add(new Employee(3, "Candy", 23, "female", 10000));
        employees.add(new Employee(4, "Tommy", 26, "male", 10000));

        companies.add(new Company(1, "alibaba", 200, this.employees.subList(0, 2)));
        companies.add(new Company(2, "chocolate factory", 50, this.employees.subList(2, 4)));
        return companies;
    }

    @GetMapping(path = "/{companyId}")
    public ResponseEntity<Object> getCompanyById(@PathVariable int companyId) {
        Company selectedCompany = this.companies.stream().filter(company -> company.getId() == companyId).findFirst().orElse(null);
        if (selectedCompany != null) {
            return new ResponseEntity<>(selectedCompany, HttpStatus.OK);
        }
        return new ResponseEntity<>("Company doesn't exist", HttpStatus.BAD_REQUEST);
    }

    @GetMapping(path = "/{companyID}/employees")
    public ResponseEntity<Object> getEmployeesInCompany(@PathVariable int companyID) {
        Company selectedCompany = this.companies.stream().filter(company -> company.getId() == companyID).findFirst().orElse(null);
        if (selectedCompany != null) {
            return new ResponseEntity<>(selectedCompany.getEmployees(), HttpStatus.OK);
        }
        return new ResponseEntity<>("Company doesn't exist", HttpStatus.BAD_REQUEST);
    }

    @GetMapping(params = {"page", "pageSize"})
    public ResponseEntity<Object> getCompaniesPage(@RequestParam(value = "page") int page, @RequestParam(value = "pageSize") int pageSize) {
        int startingIndex = (page - 1) * pageSize;
        int endingIndex = page * pageSize;
        if (this.companies.size() < startingIndex) {
            return new ResponseEntity<>("This page doesn't exists as companies list size is not big enough, please go back to page 1", HttpStatus.OK);
        } else if (this.companies.size() > startingIndex && this.companies.size() < endingIndex) {
            return new ResponseEntity<>(this.companies.subList(startingIndex, companies.size()), HttpStatus.OK);
        }
        return new ResponseEntity<>(this.companies.subList(startingIndex, endingIndex), HttpStatus.OK);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Company createNewCompany(@RequestBody Company company) {
        companies.add(company);
        return company;
    }

    @PutMapping("/{companyID}")
    public ResponseEntity<Object> updateCompanies(@PathVariable int companyId, @RequestBody Company newCompany) {
        Company selectedCompany = this.companies.stream().filter(company -> company.getId() == companyId).findFirst().orElse(null);
        if (selectedCompany != null) {
            companies.set(companies.indexOf(selectedCompany), newCompany);
            return new ResponseEntity<>(newCompany, HttpStatus.OK);
        }
        return new ResponseEntity<>("Company doesn't exist", HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/{companyID}")
    public ResponseEntity<Object> deleteCompaniesEmployees(@PathVariable int companyID) {
        Company selectedCompany = this.companies.stream().filter(company -> company.getId() == companyID).findFirst().orElse(null);
        if (selectedCompany != null && selectedCompany.getEmployees() != null) {
            selectedCompany.setEmployees(new ArrayList<>());
            return new ResponseEntity<>("Remove all the employees for the company with id " + companyID + " successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("Company doesn't exist", HttpStatus.BAD_REQUEST);
    }

}
