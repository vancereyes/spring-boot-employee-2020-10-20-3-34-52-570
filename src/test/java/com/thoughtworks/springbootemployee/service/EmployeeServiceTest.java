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

    public static final String MALE = "Male";

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
                        new Employee(1, "Vance", 25, MALE, 20000),
                        new Employee(2, "John", 23, MALE, 45000),
                        new Employee(3, "Tori", 33, "Female", 40000)));
        EmployeeService service = new EmployeeService(repository);
        //when
        List<Employee> actual = service.getAllByGender(MALE);
        //then
        Assert.assertEquals(2, actual.size());

    }

    @Test
    public void should_update_an_employee_info_when_put() {
        //given
        int id = 1;
        Employee updatedEmployee = new Employee(id, "Micah", 23, "Female", 4000);

        EmployeeRepository repository = Mockito.mock(EmployeeRepository.class);
        Mockito.when(repository.update(id, updatedEmployee)).thenReturn(updatedEmployee);
        EmployeeService service = new EmployeeService(repository);

        //when
        Employee actual = service.update(id, updatedEmployee);

        //then
        Mockito.verify(repository, Mockito.times(1)).update(id, updatedEmployee);
        Assertions.assertSame(updatedEmployee, actual);
    }

    @Test
    public void should_delete_an_employee_when_delete_given_employee_id() {
        //given
        int id = 1;
        Employee employee = new Employee();
        EmployeeRepository repository = Mockito.mock(EmployeeRepository.class);
        Mockito.when(repository.find(id)).thenReturn(employee);
        EmployeeService service = new EmployeeService(repository);

        //when
        service.delete(id);
        //then

        Mockito.verify(repository, Mockito.times(1)).find(id);
        Mockito.verify(repository, Mockito.times(1)).delete(employee);
    }


}