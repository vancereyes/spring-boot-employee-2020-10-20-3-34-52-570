package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CompanyServiceTest {

    private static final int WANTED_NUMBER_OF_INVOCATIONS = 1;

    @Test
    public void should_return_all_companies_when_get_all() {
        //given
        CompanyRepository repository = mock(CompanyRepository.class);
        when(repository.findAll()).thenReturn(asList(new Company(), new Company()));
        CompanyService service = new CompanyService(repository);
        //when
        List<Company> actual = service.getAll();
        //then
        verify(repository, times(WANTED_NUMBER_OF_INVOCATIONS)).findAll();
        assertEquals(2, actual.size());
    }

    @Test
    public void should_return_company_when_create_given_company() {
        //given
        CompanyRepository repository = mock(CompanyRepository.class);
        Company company = new Company();
        when(repository.save(company)).thenReturn(company);
        CompanyService service = new CompanyService(repository);

        //when
        Company actual = service.create(company);
        //then
        verify(repository, times(WANTED_NUMBER_OF_INVOCATIONS)).save(company);
        assertEquals(company, actual);
    }

    @Test
    public void should_return_employee_when_get_given_employee_id() {
        //given
        CompanyRepository repository = mock(CompanyRepository.class);
        Company company = new Company();
        company.setId(1);
        when(repository.find(company.getId())).thenReturn(company);
        CompanyService service = new CompanyService(repository);
        //when
        Company actual = service.get(company.getId());
        //then
        verify(repository, times(WANTED_NUMBER_OF_INVOCATIONS)).find(company.getId());
        assertSame(company, actual);
    }

    @Test
    public void should_return_an_update_company_when_update_given_id_and_updated_company() {
        //given
        int id = 1;
        Company company = new Company(id, "OOCL",
                asList(new Employee(1, "Vance", 25, "Male", 60000)));
        Company updatedCompany = new Company(id, "OOCL",
                asList(new Employee(1, "John", 23, "Male", 30000)));
        CompanyRepository repository = mock(CompanyRepository.class);

        when(repository.find(id)).thenReturn(company);
        when(repository.update(id, updatedCompany)).thenReturn(updatedCompany);

        CompanyService service = new CompanyService(repository);
        //when
        Company actual = service.update(id, updatedCompany);
        //then
        verify(repository, times(WANTED_NUMBER_OF_INVOCATIONS)).find(id);
        verify(repository, times(WANTED_NUMBER_OF_INVOCATIONS)).update(id, updatedCompany);
        assertSame(updatedCompany, actual);
    }

    @Test
    public void should_delete_all_company_employees_when_clear_employees_given_company_id() {
        //given
        String companyName = "OOCL";
        int id = 1;
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee(1, "Vance", 25, "Male", 60000));
        employees.add(new Employee(2, "John", 23, "Male", 30000));
        Company company = new Company(id, companyName, employees);
        CompanyRepository repository = mock(CompanyRepository.class);
        when(repository.find(id)).thenReturn(company);
        CompanyService service = new CompanyService(repository);

        company.getEmployees().clear();
        Company updatedCompany = company;
        //when
        Company actual = service.clearEmployees(id);
        //then
        verify(repository, times(WANTED_NUMBER_OF_INVOCATIONS)).find(id);
        verify(repository, times(WANTED_NUMBER_OF_INVOCATIONS)).update(id, updatedCompany);
        assertTrue(actual.getEmployees().isEmpty());
        assertEquals(companyName, actual.getCompanyName());
        assertEquals(id, actual.getId());
    }
}