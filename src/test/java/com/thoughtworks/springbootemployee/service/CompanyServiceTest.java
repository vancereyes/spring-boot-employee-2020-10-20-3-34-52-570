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
    private static final int COMPANY_ID = 1;
    private static final String OOCL = "OOCL";
    private static final String MALE = "Male";
    private static final String VANCE = "Vance";
    private static final String JOHN = "John";

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
        company.setId(COMPANY_ID);
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
        Company company = new Company(COMPANY_ID, OOCL,
                asList(new Employee(1, VANCE, 25, MALE, 60000)));
        Company updatedCompany = new Company(COMPANY_ID, OOCL,
                asList(new Employee(1, JOHN, 23, MALE, 30000)));
        CompanyRepository repository = mock(CompanyRepository.class);

        when(repository.find(COMPANY_ID)).thenReturn(company);
        when(repository.update(COMPANY_ID, updatedCompany)).thenReturn(updatedCompany);

        CompanyService service = new CompanyService(repository);
        //when
        Company actual = service.update(COMPANY_ID, updatedCompany);
        //then
        verify(repository, times(WANTED_NUMBER_OF_INVOCATIONS)).find(COMPANY_ID);
        verify(repository, times(WANTED_NUMBER_OF_INVOCATIONS)).update(COMPANY_ID, updatedCompany);
        assertSame(updatedCompany, actual);
    }

    @Test
    public void should_delete_all_company_employees_when_clear_employees_given_company_id() {
        //given
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee(1, VANCE, 25, MALE, 60000));
        employees.add(new Employee(2, JOHN, 23, MALE, 30000));
        Company company = new Company(COMPANY_ID, OOCL, employees);
        CompanyRepository repository = mock(CompanyRepository.class);
        when(repository.find(COMPANY_ID)).thenReturn(company);
        CompanyService service = new CompanyService(repository);

        company.getEmployees().clear();
        Company updatedCompany = company;
        //when
        Company actual = service.clearEmployees(COMPANY_ID);
        //then
        verify(repository, times(WANTED_NUMBER_OF_INVOCATIONS)).find(COMPANY_ID);
        verify(repository, times(WANTED_NUMBER_OF_INVOCATIONS)).update(COMPANY_ID, updatedCompany);
        assertTrue(actual.getEmployees().isEmpty());
        assertEquals(OOCL, actual.getCompanyName());
        assertEquals(COMPANY_ID, actual.getId());
    }

    @Test
    public void should_return_3_to_4_companies_out_of_5_when_paginate_given_page_2_and_page_size_2() {
        //given
        int pageSize = 2;
        List<Company> companies = asList(
                new Company(),
                new Company(),
                new Company(),
                new Company(),
                new Company()
        );
        CompanyRepository repository = mock(CompanyRepository.class);
        when(repository.findAll()).thenReturn(companies);
        CompanyService service = new CompanyService(repository);
        //when
        List<Company> actual = service.paginate(2, pageSize);
        //then
        verify(repository, times(WANTED_NUMBER_OF_INVOCATIONS)).findAll();
        assertEquals(pageSize, actual.size());
        assertEquals(companies.get(2), actual.get(0));
        assertEquals(companies.get(3), actual.get(1));
    }

    @Test
    public void should_return_associated_employees_when_get_employees_given_company_id() {
        //given
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee(1, VANCE, 25, MALE, 60000));
        employees.add(new Employee(2, JOHN, 23, MALE, 30000));
        Company company = new Company(COMPANY_ID, OOCL, employees);

        CompanyRepository repository = mock(CompanyRepository.class);
        when(repository.find(COMPANY_ID)).thenReturn(company);
        CompanyService service = new CompanyService(repository);
        //when
        List<Employee> actual = service.getEmployees(COMPANY_ID);
        //then
        verify(repository, times(WANTED_NUMBER_OF_INVOCATIONS)).find(COMPANY_ID);
        assertEquals(employees.size(), actual.size());
    }
}