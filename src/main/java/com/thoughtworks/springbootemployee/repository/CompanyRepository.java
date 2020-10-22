package com.thoughtworks.springbootemployee.repository;

import com.thoughtworks.springbootemployee.model.Company;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class CompanyRepository {
    private final List<Company> companies = new ArrayList<>();

    public List<Company> findAll() {
        return companies;
    }

    public Company save(Company company) {
        companies.add(company);

        return company;
    }

    public Company find(int id) {
        return companies.stream()
                .filter(company -> company.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public Company update(int id, Company updatedCompany) {
        return companies.stream()
                .filter(company -> company.getId() == id)
                .findFirst()
                .map(company -> {
                    companies.remove(company);
                    companies.add(updatedCompany);

                    return updatedCompany;
                })
                .orElse(null);
    }

    public void delete(Company company) {

    }
}
