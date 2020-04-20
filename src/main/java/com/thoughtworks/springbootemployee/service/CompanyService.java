package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompanyService {
    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    public List<Company> getCompanies() {
        return companyRepository.findAll();
    }

    public Company getCompanyById(Integer companyId) {
        return companyRepository.findById(companyId).orElse(null);
    }

    public List<Employee> getEmployeesInCompany(Integer companyId) {
        Company targetCompany = companyRepository.findById(companyId).orElse(null);
        return targetCompany.getEmployees();
    }

    public List<Company> getCompaniesWithPagination(Integer page, Integer pageSize) {
        return companyRepository.findAll(PageRequest.of(page - 1, pageSize)).getContent();
    }

    public void createCompany(Company company) {
        companyRepository.save(company);
    }

    public Boolean updateByCompanyId(Integer companyId, Company newCompany) {
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

        companyRepository.save(targetCompany);
        return true;
    }


    public boolean deleteEmployeesByCompanyId(Integer companyID) {
        Company selectedCompany = companyRepository.findById(companyID).orElse(null);
        if (selectedCompany != null && selectedCompany.getEmployees().size() != 0) {
            List<Employee> employeeList = employeeRepository.findAll().stream()
                    .filter(employee -> employee.getCompanyId().equals(companyID))
                    .collect(Collectors.toList());

            for (Employee employee : employeeList) {
                employee.setCompanyId(null);
                employeeRepository.save(employee);
            }

            return true;
        }
        return false;
    }
}
