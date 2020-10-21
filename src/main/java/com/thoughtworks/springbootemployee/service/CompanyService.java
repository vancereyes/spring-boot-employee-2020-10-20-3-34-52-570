package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService {

    private final CompanyRepository repository;

    public CompanyService(CompanyRepository repository) {
        this.repository = repository;
    }

    public List<Company> getAll() {
        return repository.findAll();
    }

    public Company create(Company company) {
        return repository.save(company);
    }

    public Company get(int id) {
        return repository.find(id);
    }

    public Company update(int id, Company updatedCompany) {
        Company company = get(id);
        if (company == null) {
            return null;
        }

        repository.update(id, updatedCompany);

        return updatedCompany;
    }
}
