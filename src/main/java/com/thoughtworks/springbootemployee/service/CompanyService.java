package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
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
        return companyRepository.findById(companyId).orElse(null);
    }

    public List<Employee> getEmployeesInCompany(int companyId) {
        Company targetCompany = companyRepository.findById(companyId).orElse(null);
        return targetCompany.getEmployees();
    }

    public List<Company> getCompaniesWithPagination(Integer page, Integer pageSize) {
        return companyRepository.findAll(PageRequest.of(page - 1, pageSize)).getContent();
    }

    public void createCompany(Company company) {
        companyRepository.save(company);
    }

    public Boolean updateByCompanyId(int companyId, Company newCompany) {
        Company targetCompany = companyRepository.findById(companyId).orElse(null);

        if (targetCompany == null) {
            return false;
        }

        if (newCompany.getCompanyName() != null) {
            targetCompany.setCompanyName(newCompany.getCompanyName());
        }

        if (newCompany.getEmployeesNumber() != null) {
            targetCompany.setEmployeesNumber(newCompany.getEmployeesNumber());
        }

        if (newCompany.getEmployees() != null) {
            targetCompany.setEmployees(newCompany.getEmployees());
        }

        companyRepository.save(targetCompany);
        return true;
    }


    public boolean deleteEmployeesByCompanyId(int companyID) {
        Company selectedCompany = companyRepository.findById(companyID).orElse(null);
        if (selectedCompany != null && selectedCompany.getEmployees() != null) {
            selectedCompany.setEmployees(new ArrayList<>());
            companyRepository.save(selectedCompany);
            return true;
        }
        return false;
    }
}
