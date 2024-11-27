package com.apress.catalog.service;

import com.apress.catalog.dto.CountryDTO;
import com.apress.catalog.model.Country;
import com.apress.catalog.repository.CountryRepository;
import com.apress.catalog.repository.StateRepository;

import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CountryServiceTest2 {

    @Mock
    private CountryRepository countryRepository;

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
        Country country = new Country();
        country.setId(1L);
        country.setName("Argentina");
        
        when(countryRepository.findById(1L)).thenReturn(Mono.just(country));

        CountryDTO result = countryService.getById(1L);
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Argentina", result.getName());
    }

    @Test
    public void testSave() {
        CountryDTO countryDTO = new CountryDTO();
        countryDTO.setName("Argentina");

        Country country = new Country();
        country.setId(1L);
        country.setName("Argentina");

        when(countryRepository.save(any(Country.class))).thenReturn(Mono.just(country));

        CountryDTO result = countryService.save(countryDTO).block();
        assertNotNull(result);
        assertEquals("Argentina", result.getName());
    }

    @Test
    public void testUpdate() {
        CountryDTO countryDTO = new CountryDTO();
        countryDTO.setId(1L);
        countryDTO.setName("Updated Argentina");

        Country country = new Country();
        country.setId(1L);
        country.setName("Updated Argentina");

        when(countryRepository.save(any(Country.class))).thenReturn(Mono.just(country));

        CountryDTO result = countryService.update(countryDTO).block();
        assertNotNull(result);
        assertEquals("Updated Argentina", result.getName());
    }

    @Test
    @Transactional
    @Rollback
    public void should_rollback_transaction_on_exception() {
        CountryDTO countryDTO = new CountryDTO();
        countryDTO.setEnabled(Boolean.TRUE);
        countryDTO.setCode("ARG");
        countryDTO.setName("Argentina");

        // Simulate an exception to trigger rollback
        doThrow(new RuntimeException("Simulated Exception")).when(countryRepository).save(any(Country.class));
        // Mock findById return void value
        when(countryRepository.findById(2L)).thenReturn(Mono.empty());

        assertThrows(RuntimeException.class, () -> countryService.save(countryDTO).block());
        // Verify that the save method was called
        verify(countryRepository, times(1)).save(any(Country.class));
        
        // Verify that the transaction was rolled back
        Mono<Country> result = countryRepository.findById(2L);
        assertTrue(result.blockOptional().isEmpty());
    }
}
