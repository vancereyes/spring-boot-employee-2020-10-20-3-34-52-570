package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import org.junit.jupiter.api.Test;

import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
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
}