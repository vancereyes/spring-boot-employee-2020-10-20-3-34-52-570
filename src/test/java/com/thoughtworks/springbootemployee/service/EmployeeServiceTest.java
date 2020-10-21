package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
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

}