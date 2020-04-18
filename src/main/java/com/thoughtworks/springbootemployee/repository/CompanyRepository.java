package com.thoughtworks.springbootemployee.repository;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class CompanyRepository {
    private List<Company> companies = new ArrayList<>();

    public void setCompanies(List<Company> companies) {
        this.companies = companies;
    }

    public CompanyRepository(List<Company> companies) {
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

    public Company findCompanyById(int companyId) {
        return this.companies.stream().
                filter(company -> company.getId() == companyId).
                findFirst().orElse(null);
    }

    public void save(Company company) {
        this.companies.add(company);
    }

    public void update(int companyId, Company newCompany) {
        this.companies.set(companyId,newCompany);
    }

}
