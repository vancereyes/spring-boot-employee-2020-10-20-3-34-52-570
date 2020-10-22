package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.service.CompanyService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/companies")
public class CompanyController {

    private CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping
    public List<Company> getAll() {
        return companyService.getAll();
    }

    @GetMapping("/{id}")
    public Company get(@PathVariable int id) {
        return companyService.get(id);
    }

    @PostMapping
    public Company create(@RequestBody Company company) {
        return companyService.create(company);
    }

    @DeleteMapping("/{id}")
    public Company delete(@PathVariable int id) {
        return companyService.clearEmployees(id);
    }

    @GetMapping(params = {"page", "pageSize"})
    public List<Company> paginate(@RequestParam int page, @RequestParam int pageSize) {
        return companyService.paginate(page, pageSize);
    }

    @PutMapping("/{id}")
    public Company update(@PathVariable int id, @RequestBody Company updatedCompany) {
        return companyService.update(id, updatedCompany);
    }

    @GetMapping("/{id}/employees")
    public List<Employee> getCompanyEmployees(@PathVariable int id) {
        return companyService.getEmployees(id);
    }
}
