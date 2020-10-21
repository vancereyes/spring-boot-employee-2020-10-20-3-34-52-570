package com.thoughtworks.springbootemployee.controller;

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
@RequestMapping("/employees")

public class EmployeeController {

    private final List<Employee> employees = new ArrayList<>();

    @GetMapping
    public List<Employee> getAll() {
        return employees;
    }

    @GetMapping("/{id}")
    public Employee get(@PathVariable int id) {
        return employees.stream()
                .filter(employee -> employee.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @PostMapping
    public Employee create(@RequestBody Employee employee) {
        employees.add(employee);
        return employee;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        employees.stream()
                .filter(employee -> employee.getId() == id)
                .findFirst()
                .ifPresent(employee -> employees.remove(employee));
    }

    @GetMapping(params = {"page", "pageSize"})
    public List<Employee> paginate(@RequestParam int page, @RequestParam int pageSize) {
        return employees.stream()
                .skip((page - 1) * pageSize)
                .limit(pageSize)
                .collect(Collectors.toList());
    }

    @PutMapping("/{id}")
    public void update(@PathVariable int id, @RequestBody Employee updatedEmployee) {
        employees.stream()
                .filter(employee -> employee.getId() == id)
                .findFirst()
                .ifPresent(employee -> {
                    employees.remove(employee);
                    employees.add(updatedEmployee);
                });
    }

    @GetMapping(params = "gender")
    public List<Employee> searchByGender(@RequestParam String gender) {
        return employees.stream()
                .filter(employee -> employee.getGender().equalsIgnoreCase(gender))
                .collect(Collectors.toList());
    }
}
