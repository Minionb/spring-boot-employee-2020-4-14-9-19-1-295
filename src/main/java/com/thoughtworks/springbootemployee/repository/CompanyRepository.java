package com.thoughtworks.springbootemployee.repository;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional
public interface CompanyRepository extends JpaRepository<Company, Integer> {
    @Modifying
    @Query(value = "ALTER TABLE COMPANY ALTER COLUMN ID RESTART WITH 1", nativeQuery = true)
    void resetAutoIncrement();
}
