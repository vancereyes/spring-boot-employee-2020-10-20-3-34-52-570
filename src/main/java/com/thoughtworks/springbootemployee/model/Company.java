package com.thoughtworks.springbootemployee.model;

import java.util.ArrayList;
import java.util.List;

public class Company {
    private int id;
    private String companyName;
    private List<Employee> employees;

    public Company() {
        employees = new ArrayList<>();
    }

    public Company(int id, String companyName, List<Employee> employees) {
        this.id = id;
        this.companyName = companyName;
        this.employees = employees;
    }

    public int getId() {
        return id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public int getEmployeesNumber() {
        return employees.size();
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setId(int id) {

    }
}
