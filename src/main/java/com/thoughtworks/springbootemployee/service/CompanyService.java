package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.respository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Scanner;
@Service
public class CompanyService {
    @Autowired
    private CompanyRepository companyRepository;
    public List<Company> getAll() {
        return companyRepository.findAll();
    }

    public ResponseEntity<Object> getById(int companyId) {
        return companyRepository.findById(companyId);
    }

    public ResponseEntity<Object> getEmployeesByCompanyId(int companyId) {
        return companyRepository.findEmployeesById(companyId);
    }

    public ResponseEntity<Object> getCompaniesInPage(int page, int pageSize) {
        return companyRepository.findCompaniesInPage(page,pageSize);
    }

    public Company createCompany(Company company) {
        return companyRepository.create(company);
    }

    public ResponseEntity<Object> updateByCompanyId(int companyId, Company newCompany) {
        return companyRepository.update(companyId,newCompany);
    }


    public ResponseEntity<Object> deleteEmployeesByCompanyId(int companyID) {
        return companyRepository.delete(companyID);
    }
}
