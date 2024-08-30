package com.apress.catalog.service;

import com.apress.catalog.dto.CurrencyDTO;
import com.apress.catalog.mapper.ApiMapper;
import com.apress.catalog.model.Currency;
import com.apress.catalog.repository.CurrencyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CurrencyServiceTest {

    @Mock
    private CurrencyRepository repository;

    @Mock
    private ApiMapper apiMapper;

    @InjectMocks
    private CurrencyService currencyService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetById() {
        Long id = 1L;
        Currency currency = new Currency();
        CurrencyDTO currencyDTO = new CurrencyDTO();

        when(repository.findById(id)).thenReturn(Optional.of(currency));
        when(apiMapper.entityToDTO(currency)).thenReturn(currencyDTO);

        CurrencyDTO result = currencyService.getById(id);

        assertNotNull(result);
        assertEquals(currencyDTO, result);
        verify(repository, times(1)).findById(id);
        verify(apiMapper, times(1)).entityToDTO(currency);
    }

    @Test
    public void testGetById_NotFound() {
        Long id = 1L;

        when(repository.findById(id)).thenReturn(Optional.empty());

        CurrencyDTO result = currencyService.getById(id);

        assertNull(result);
        verify(repository, times(1)).findById(id);
        verify(apiMapper, times(0)).entityToDTO(any(Currency.class));
    }
}