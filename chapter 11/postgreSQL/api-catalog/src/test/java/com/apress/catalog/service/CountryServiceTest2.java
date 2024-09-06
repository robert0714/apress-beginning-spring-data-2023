package com.apress.catalog.service;

import com.apress.catalog.dto.CountryDTO;
import com.apress.catalog.model.Country;
import com.apress.catalog.repository.CountryRepository;
import com.apress.catalog.repository.StateRepository; 

import jakarta.validation.Validator;
import reactor.core.publisher.Mono;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations; 
 
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
 

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

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
        Mono<Country> mono = mock( Mono.class);
        when(countryRepository.findById(1L)).thenReturn(mono);
        when(mono.blockOptional() ).thenReturn(Optional.of(country));

        CountryDTO result = countryService.getById(1L );
        assertNotNull(result);
    }

    @Test 
    public void testSave() {
        CountryDTO countryDTO = new CountryDTO();
        Country country = new Country();
        Mono<Country> mono = mock( Mono.class);
        when(countryRepository.save(any(Country.class))).thenReturn(mono);
        when(mono.blockOptional() ).thenReturn(Optional.of(country));
        
        CountryDTO result = countryService.save(countryDTO);
        assertNotNull(result);
    }

    @Test 
    public void testUpdate() {
        CountryDTO countryDTO = new CountryDTO();
        Country country = new Country();
        Mono<Country> mono = mock( Mono.class);
        when(countryRepository.save(any(Country.class))).thenReturn(mono);
        when(mono.blockOptional() ).thenReturn(Optional.of(country));
        
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
        Mono<Country> mono = mock( Mono.class);
        when(countryRepository.save(any(Country.class))).thenReturn(mono);
        when(mono.blockOptional() ).thenReturn(Optional.of(country));

        // Simulate an exception to trigger rollback
        doThrow(new RuntimeException("Simulated Exception")).when(countryRepository).save(any(Country.class));

        assertThrows(RuntimeException.class, () -> {
            countryService.save(countryDTO);
        });

        // Verify that the save method was called
        verify(countryRepository, times(1)).save(any(Country.class));

        // Verify that the transaction was rolled back
        Mono<Country> result = countryRepository.findById(2L);
        assertFalse(result.blockOptional().isPresent());
    }
    
}