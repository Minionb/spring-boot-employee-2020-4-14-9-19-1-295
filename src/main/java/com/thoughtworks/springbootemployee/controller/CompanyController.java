package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/companies")
public class CompanyController {
    @Autowired
    private CompanyService companyService;

    @GetMapping
    public List<Company> getAllCompanies() {
        return companyService.getCompanies();
    }

    @GetMapping(path = "/{companyId}")
    public Company getCompany(@PathVariable int companyId) {
        return companyService.getCompanyById(companyId);
    }

    @GetMapping(path = "/{companyId}/employees")
    public List<Employee> getEmployeesInCompany(@PathVariable int companyId) {
        return companyService.getEmployeesInCompany(companyId);
    }

    @GetMapping(params = {"page", "pageSize"})
    public ResponseEntity<Object> getCompaniesPage(@RequestParam(value = "page") int page, @RequestParam(value = "pageSize") int pageSize) {
        return new ResponseEntity<>(companyService.getCompaniesWithPagination(page, pageSize), HttpStatus.OK);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Company createNewCompany(@RequestBody Company company) {
         companyService.createCompany(company);
         return company;
    }

    @PutMapping("/{companyId}")
    public ResponseEntity<Object> updateCompanies(@PathVariable int companyId, @RequestBody Company newCompany) {
        boolean isUpdate = companyService.updateByCompanyId(companyId, newCompany);
        if (!isUpdate) {
            return new ResponseEntity<>("Error, company is not exist.", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(newCompany, HttpStatus.OK);
    }

    @DeleteMapping("/{companyId}")
    public ResponseEntity<Object> deleteCompaniesEmployees(@PathVariable int companyId) {
        boolean isDelete = companyService.deleteEmployeesByCompanyId(companyId);
        if (!isDelete) {
            return new ResponseEntity<>("Error, employee list in company is not exist", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Remove company with id " + companyId + " successfully", HttpStatus.OK);
    }

}
