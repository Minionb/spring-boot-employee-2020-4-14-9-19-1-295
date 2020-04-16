package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private CompanyService companyService;

    public CompanyController(){
        this.employees.add(new Employee(1, "Hilary", 23, "female", 10000));
        this.employees.add(new Employee(2, "Jay", 30, "male", 10000));
        this.employees.add(new Employee(3, "Candy", 23, "female", 10000));
        this.employees.add(new Employee(4, "Tommy", 26, "male", 10000));

        this.companies.add(new Company(1, "Alibaba", 200, this.employees.subList(0, 2)));
        this.companies.add(new Company(2, "Chocolate Factory", 50, this.employees.subList(2, 4)));

    }
    @GetMapping
    public List<Company> getAllCompanies() {
        return companyService.getAll();
    }

    @GetMapping(path = "/{companyId}")
    public ResponseEntity<Object> getCompanyById(@PathVariable int companyId) {
        return companyService.getById(companyId);
    }

    @GetMapping(path = "/{companyId}/employees")
    public ResponseEntity<Object> getEmployeesInCompany(@PathVariable int companyId) {
        return companyService.getEmployeesByCompanyId(companyId);
    }

    @GetMapping(params = {"page", "pageSize"})
    public ResponseEntity<Object> getCompaniesPage(@RequestParam(value = "page") int page, @RequestParam(value = "pageSize") int pageSize) {
        return companyService.getCompaniesInPage(page,pageSize);

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Company createNewCompany(@RequestBody Company company) {
        return companyService.createCompany(company);
    }

    @PutMapping("/{companyId}")
    public ResponseEntity<Object> updateCompanies(@PathVariable int companyId, @RequestBody Company newCompany) {
        return companyService.updateByCompanyId(companyId,newCompany);
    }

    @DeleteMapping("/{companyID}")
    public ResponseEntity<Object> deleteCompaniesEmployees(@PathVariable int companyID) {
        return companyService.deleteEmployeesByCompanyId(companyID);
    }

}
