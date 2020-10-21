package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static java.util.Arrays.asList;

class EmployeeServiceTest {

    @Test
    public void should_get_all_employees_when_get_all() {
        //given
        EmployeeRepository repository = Mockito.mock(EmployeeRepository.class);

        Mockito.when(repository.findAll()).thenReturn(asList(new Employee(), new Employee()));
        EmployeeService service = new EmployeeService(repository);
        //when
        List<Employee> actual = service.getAll();
        //then
        Assertions.assertEquals(2, actual.size());
    }

    @Test
    public void should_add_employee_when_add_given_employee() {
        //given
        EmployeeRepository repository = Mockito.mock(EmployeeRepository.class);
        Employee employee = new Employee();
        Mockito.when(repository.save(employee)).thenReturn(employee);
        EmployeeService service = new EmployeeService(repository);

        //when
        Employee actual = service.create(employee);
        //then
        Assertions.assertEquals(employee, actual);

    }

    @Test
    public void should_return_get_employee_when_get_given_employee_id() {
        //given
        EmployeeRepository repository = Mockito.mock(EmployeeRepository.class);
        Employee employee = new Employee();
        employee.setId(1);
        Mockito.when(repository.find(employee.getId())).thenReturn(employee);
        EmployeeService service = new EmployeeService(repository);
        //when
        Employee actual = service.get(employee.getId());
        //then
        Assertions.assertSame(employee, actual);
    }

    @Test
    public void should_return_all_employee_when_get_given_gender() {
        //given
        EmployeeRepository repository = Mockito.mock(EmployeeRepository.class);
        Mockito.when(repository.findAll())
                .thenReturn(asList(
                        new Employee(1, "Vance", 25, "Male", 20000),
                        new Employee(2, "John", 23, "Male", 45000),
                        new Employee(3, "Tori", 33, "Female", 40000)));
        EmployeeService service = new EmployeeService(repository);
        //when
        List<Employee> actual = service.getAllByGender("Male");
        //then
        Assert.assertEquals(2, actual.size());

    }


}