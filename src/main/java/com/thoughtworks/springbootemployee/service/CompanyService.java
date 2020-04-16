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
}
