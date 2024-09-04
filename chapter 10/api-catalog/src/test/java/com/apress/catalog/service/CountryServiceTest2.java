package com.apress.catalog.service;

import com.apress.catalog.dto.CountryDTO;
import com.apress.catalog.model.Country;
import com.apress.catalog.repository.CountryRepository;
import com.apress.catalog.repository.CustomCountryRepository;
import com.apress.catalog.repository.StateRepository;
//import com.apress.catalog.repository.impl.CustomCountryRepositoryImpl;

import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations; 
 
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
 

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class CountryServiceTest2 {

    @Mock
    private CountryRepository countryRepository;
    
//    @Mock
//    private CustomCountryRepository countryRepository;

    @Mock
    private StateRepository stateRepository;

    @Mock
    private Validator validator;

    @InjectMocks
    private CountryService countryService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test 
    public void testGetById() {
    	UUID uuid = UUID.randomUUID();
        Country country = new Country();
        country.setId(uuid);
        when(countryRepository.findById(uuid)).thenReturn(Optional.of(country));

        CountryDTO result = countryService.getById(uuid );
        assertNotNull(result);
    }

    @Test 
    public void testSave() {
        CountryDTO countryDTO = new CountryDTO();
        Country country = new Country();
        when(countryRepository.save(any(Country.class))).thenReturn(country);

        CountryDTO result = countryService.save(countryDTO);
        assertNotNull(result);
    }

    @Test 
    public void testUpdate() {
        CountryDTO countryDTO = new CountryDTO();
        Country country = new Country();
        when(countryRepository.save(any(Country.class))).thenReturn(country);

        CountryDTO result = countryService.update(countryDTO);
        assertNotNull(result);
    }
    @Test
    @Transactional
    @Rollback
    public void should_rollback_transaction_on_exception() {
        CountryDTO countryDTO = new CountryDTO();
        countryDTO.setEnabled(Boolean.TRUE);
        countryDTO.setCode("ARG");
        countryDTO.setName("Argentina");

        Country country = new Country();
        when(countryRepository.save(any(Country.class))).thenReturn(country);

        // Simulate an exception to trigger rollback
        doThrow(new RuntimeException("Simulated Exception")).when(countryRepository).save(any(Country.class));

        assertThrows(RuntimeException.class, () -> {
            countryService.save(countryDTO);
        });

        // Verify that the save method was called
        verify(countryRepository, times(1)).save(any(Country.class));

        // Verify that the transaction was rolled back
        long result = countryRepository.count();
        assertEquals(0 , result);
    }
    
}