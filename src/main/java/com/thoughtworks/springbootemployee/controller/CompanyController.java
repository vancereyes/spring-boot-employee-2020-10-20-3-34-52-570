package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/companies")

public class CompanyController {

    private final List<Company> companies = new ArrayList<>();

    @GetMapping
    public List<Company> getAll() {
        return companies;
    }

    @GetMapping("/{id}")
    public Company get(@PathVariable int id) {
        return companies.stream()
                .filter(company -> company.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @PostMapping
    public Company create(@RequestBody Company company) {
        companies.add(company);

        return company;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        companies.stream()
                .filter(company -> company.getId() == id)
                .findFirst()
                .ifPresent(company -> companies.remove(company));
    }

    @GetMapping(params = {"page", "pageSize"})
    public List<Company> paginate(@RequestParam int page, @RequestParam int pageSize) {
        return companies.stream()
                .skip(pageSize * (page - 1))
                .limit(pageSize)
                .collect(Collectors.toList());
    }

    @PutMapping("/{id}")
    public Company update(@PathVariable int id, @RequestBody Company updatedCompany) {
        return companies.stream()
                .filter(e -> e.getId() == id)
                .findFirst()
                .map(e -> {
                    companies.remove(e);
                    companies.add(updatedCompany);

                    return updatedCompany;
                })
                .orElse(null);
    }

    @GetMapping("/{id}/employees")
    public List<Employee> getCompanyEmployees(@PathVariable int id) {
        return companies.stream()
                .filter(company -> company.getId() == id)
                .findFirst()
                .map(Company::getEmployees)
                .orElse(null);
    }
}
