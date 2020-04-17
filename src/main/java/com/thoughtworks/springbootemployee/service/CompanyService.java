package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.respository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompanyService {
    @Autowired
    private CompanyRepository companyRepository;

    public List<Company> getCompanies() {
        return companyRepository.findAll();
    }

    public Company getCompanyById(int companyId) {
        return companyRepository.findCompanyById(companyId);
    }

    public List<Employee> getEmployeesInCompany(int companyId) {
        Company targetCompany = companyRepository.findCompanyById(companyId);
        return targetCompany.getEmployees();
    }

    public List<Company> getCompaniesWithPagination(int page, int pageSize) {
        int startingIndex = (page - 1) * pageSize;
        int endingIndex = page * pageSize;
        List<Company> companies = this.companyRepository.findAll();
        return companies.stream()
                .sorted(Comparator.comparing(Company::getId))
                .skip(startingIndex)
                .limit(endingIndex)
                .collect(Collectors.toList());
    }

    public void createCompany(Company company) {
        companyRepository.save(company);
    }

    public Boolean updateByCompanyId(int companyId, Company newCompany) {
        Company targetCompany = companyRepository.findCompanyById(companyId);
        if (targetCompany == null) {
            return false;
        }
        companyRepository.update(companyId, newCompany);
        return true;
    }


    public boolean deleteEmployeesByCompanyId(int companyID) {
        Company selectedCompany = companyRepository.findAll().stream().
                filter(company -> company.getId() == companyID).findFirst().
                orElse(null);
        if (selectedCompany != null && selectedCompany.getEmployees() != null) {
            selectedCompany.setEmployees(new ArrayList<>());
            return true;
        }
        return false;
    }
}
