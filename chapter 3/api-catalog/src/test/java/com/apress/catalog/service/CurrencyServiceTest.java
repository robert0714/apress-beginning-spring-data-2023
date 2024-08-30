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

    @InjectMocks
    private CurrencyService currencyService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void should_get_currency_by_id() {
        Currency currency = new Currency(1L, "USD", "Dollar", true, 2);
        when(repository.findById(1L)).thenReturn(Optional.of(currency));

        CurrencyDTO currencyDTO = currencyService.getById(1L);

        assertNotNull(currencyDTO);
        assertEquals(1L, currencyDTO.getId());
        assertEquals("USD", currencyDTO.getCode());
        assertEquals("Dollar", currencyDTO.getDescription());
        assertTrue(currencyDTO.getEnable());
        assertEquals(2, currencyDTO.getDecimalPlaces());
    }

    @Test
    public void should_save_currency() {
        Currency currency = new Currency(2L, "EUR", "Euro", true, 2);
        CurrencyDTO currencyDTO = ApiMapper.INSTANCE.entityToDTO(currency);
        when(repository.save(any(Currency.class))).thenReturn(currency);

        CurrencyDTO savedCurrency = currencyService.save(currencyDTO);

        assertNotNull(savedCurrency);
        assertEquals(currencyDTO, savedCurrency);
    }

    @Test
    public void should_update_currency() {
        Currency currency = new Currency(3L, "GBP", "Pound", true, 2);
        CurrencyDTO currencyDTO = ApiMapper.INSTANCE.entityToDTO(currency);
        when(repository.save(any(Currency.class))).thenReturn(currency);

        CurrencyDTO updatedCurrency = currencyService.update(currencyDTO);

        assertNotNull(updatedCurrency);
        assertEquals(currencyDTO, updatedCurrency);
    }

    @Test
    public void should_delete_currency() {
        doNothing().when(repository).deleteById(4L);

        currencyService.delete(4L);
 
        verify(repository, times(1)).deleteById(4L);
    }
}