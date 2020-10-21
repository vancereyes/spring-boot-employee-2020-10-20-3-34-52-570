package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;

import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class EmployeeServiceTest {

    public static final String MALE = "Male";
    public static final int WANTED_NUMBER_OF_INVOCATIONS = 1;

    @Test
    public void should_return_all_employees_when_get_all() {
        //given
        EmployeeRepository repository = mock(EmployeeRepository.class);

        when(repository.findAll()).thenReturn(asList(new Employee(), new Employee()));
        EmployeeService service = new EmployeeService(repository);
        //when
        List<Employee> actual = service.getAll();
        //then
        verify(repository, times(WANTED_NUMBER_OF_INVOCATIONS)).findAll();
        assertEquals(2, actual.size());
    }

    @Test
    public void should_return_employee_when_create_given_employee() {
        //given
        EmployeeRepository repository = mock(EmployeeRepository.class);
        Employee employee = new Employee();
        when(repository.save(employee)).thenReturn(employee);
        EmployeeService service = new EmployeeService(repository);

        //when
        Employee actual = service.create(employee);
        //then
        verify(repository, times(WANTED_NUMBER_OF_INVOCATIONS)).save(employee);
        assertEquals(employee, actual);

    }

    @Test
    public void should_return_employee_when_get_given_employee_id() {
        //given
        EmployeeRepository repository = mock(EmployeeRepository.class);
        Employee employee = new Employee();
        employee.setId(1);
        when(repository.find(employee.getId())).thenReturn(employee);
        EmployeeService service = new EmployeeService(repository);
        //when
        Employee actual = service.get(employee.getId());
        //then
        verify(repository, times(WANTED_NUMBER_OF_INVOCATIONS)).find(employee.getId());
        assertSame(employee, actual);
    }

    @Test
    public void should_return_all_male_employees_when_get_all_by_gender_given_male() {
        //given
        EmployeeRepository repository = mock(EmployeeRepository.class);
        when(repository.findAll())
                .thenReturn(asList(
                        new Employee(1, "Vance", 25, MALE, 20000),
                        new Employee(2, "John", 23, MALE, 45000),
                        new Employee(3, "Tori", 33, "Female", 40000)));
        EmployeeService service = new EmployeeService(repository);
        //when
        List<Employee> actual = service.getAllByGender(MALE);
        //then
        verify(repository, times(WANTED_NUMBER_OF_INVOCATIONS)).findAll();
        assertEquals(2, actual.size());

    }

    @Test
    public void should_return_an_update_employee_when_update_given_id_and_updated_employee() {
        //given
        int id = 1;
        Employee updatedEmployee = new Employee(id, "Micah", 23, "Female", 4000);
        EmployeeRepository repository = mock(EmployeeRepository.class);
        when(repository.update(id, updatedEmployee)).thenReturn(updatedEmployee);
        EmployeeService service = new EmployeeService(repository);
        //when
        Employee actual = service.update(id, updatedEmployee);
        //then
        verify(repository, times(WANTED_NUMBER_OF_INVOCATIONS)).update(id, updatedEmployee);
        assertSame(updatedEmployee, actual);
    }

    @Test
    public void should_delete_an_employee_when_delete_given_employee_id() {
        //given
        int id = 1;
        Employee employee = new Employee();
        EmployeeRepository repository = mock(EmployeeRepository.class);
        when(repository.find(id)).thenReturn(employee);
        EmployeeService service = new EmployeeService(repository);
        //when
        service.delete(id);
        //then
        verify(repository, times(WANTED_NUMBER_OF_INVOCATIONS)).find(id);
        verify(repository, times(WANTED_NUMBER_OF_INVOCATIONS)).delete(employee);
    }

    @Test
    public void should_return_3_to_4_employee_out_of_5_when_paginate_given_page_2_and_page_size_2() {
        //given
        int pageSize = 2;
        List<Employee> employees = asList(new Employee(),
                new Employee(),
                new Employee(),
                new Employee(),
                new Employee());
        EmployeeRepository repository = mock(EmployeeRepository.class);
        when(repository.findAll()).thenReturn(employees);
        EmployeeService service = new EmployeeService(repository);
        //when
        List<Employee> actual = service.paginate(2, pageSize);
        //then
        verify(repository, times(WANTED_NUMBER_OF_INVOCATIONS)).findAll();
        assertEquals(pageSize, actual.size());
        assertEquals(employees.get(2), actual.get(0));
        assertEquals(employees.get(3), actual.get(1));
    }

    @Test
    public void should_return_null_when_update_given_id_does_not_exist() {
        //given
        int id = 1;
        Employee updatedEmployee = new Employee();
        EmployeeRepository repository = mock(EmployeeRepository.class);
        when(repository.update(id, updatedEmployee)).thenReturn(null);
        EmployeeService service = new EmployeeService(repository);
        //when
        Employee actual = service.update(id, updatedEmployee);
        //then
        verify(repository, times(WANTED_NUMBER_OF_INVOCATIONS)).update(id, updatedEmployee);
        assertNull(actual);
    }
}