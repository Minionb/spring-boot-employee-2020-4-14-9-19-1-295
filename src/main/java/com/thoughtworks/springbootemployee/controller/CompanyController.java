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
    @Autowired
    private CompanyService companyService;

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
        return companyService.getCompaniesInPage(page, pageSize);

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Company createNewCompany(@RequestBody Company company) {
        return companyService.createCompany(company);
    }

    @PutMapping("/{companyId}")
    public ResponseEntity<Object> updateCompanies(@PathVariable int companyId, @RequestBody Company newCompany) {
        return companyService.updateByCompanyId(companyId, newCompany);
    }

    @DeleteMapping("/{companyID}")
    public ResponseEntity<Object> deleteCompaniesEmployees(@PathVariable int companyID) {
        return companyService.deleteEmployeesByCompanyId(companyID);
    }

}
