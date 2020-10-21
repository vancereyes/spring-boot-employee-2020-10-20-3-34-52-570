package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    public Company clearEmployees(int id) {
        Company company = get(id);
        if (company == null) {
            return null;
        }

        company.getEmployees().clear();
        repository.update(id, company);

        return company;
    }

    public List<Company> paginate(int page, int pageSize) {
        return getAll().stream()
                .skip(pageSize * (page - 1))
                .limit(pageSize)
                .collect(Collectors.toList());
    }

    public List<Employee> getEmployees(int id) {
        return null;
    }
}
