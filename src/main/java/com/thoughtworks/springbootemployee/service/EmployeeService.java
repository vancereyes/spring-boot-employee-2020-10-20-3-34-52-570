package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeService {


    private final EmployeeRepository repository;

    public EmployeeService(EmployeeRepository repository) {

        this.repository = repository;
    }

    public List<Employee> getAll() {
        return repository.findAll();
    }

    public Employee create(Employee employee) {
        return repository.save(employee);
    }

    public Employee get(int id) {
        return repository.find(id);
    }

    public List<Employee> getAllByGender(String gender) {
        return getAll().stream()
                .filter(employee -> employee.getGender().equalsIgnoreCase(gender))
                .collect(Collectors.toList());
    }

    public Employee update(int id, Employee updatedEmployee) {
        return repository.update(id, updatedEmployee);
    }
}
