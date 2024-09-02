package com.apress.catalog.service;

import com.apress.catalog.dto.CurrencyDTO;
import com.apress.catalog.mapper.ApiMapper;
import com.apress.catalog.model.Currency;
import com.apress.catalog.repository.CurrencyRepository;
import jakarta.validation.Validator;
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

public class CurrencyServiceTest {
    @Mock
    private CurrencyRepository repository;

    @Mock
    private Validator validator;

    @InjectMocks
    private CurrencyService currencyService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void should_get_currency_by_id() {
        Currency currency = new Currency(1L, "USD", "Dollar", true, 2, "$");
        when(repository.findById(1L)).thenReturn(Optional.of(currency));
        CurrencyDTO currencyDTO = ApiMapper.INSTANCE.entityToDTO(currency);

        CurrencyDTO result = currencyService.getById(1L);

        assertNotNull(result);
        assertEquals(currencyDTO, result);
    }

    @Test
    public void should_save_currency() {
        CurrencyDTO currencyDTO = new CurrencyDTO(2L, "EUR", "Euro", true, 2);
        Currency currency = ApiMapper.INSTANCE.DTOToEntity(currencyDTO);
        when(repository.save(any(Currency.class))).thenReturn(currency);

        CurrencyDTO result = currencyService.save(currencyDTO);

        assertNotNull(result);
        assertEquals(currencyDTO, result);
    }

    @Test
    public void should_update_currency() {
        CurrencyDTO currencyDTO = new CurrencyDTO(3L, "GBP", "Pound", true, 2);
        Currency currency = ApiMapper.INSTANCE.DTOToEntity(currencyDTO);
        when(repository.save(any(Currency.class))).thenReturn(currency);

        CurrencyDTO result = currencyService.update(currencyDTO);

        assertNotNull(result);
        assertEquals(currencyDTO, result);
    }

    @Test
    public void should_delete_currency() {
        Currency currency = new Currency(4L, "JPY", "Yen", true, 2, "$");
        when(repository.findById(4L)).thenReturn(Optional.of(currency));

        currencyService.delete(4L);

        verify(repository, times(1)).save(currency);
        assertFalse(currency.getEnabled());
    }
    @Test
    @Rollback
    @Transactional
    public void should_rollback_transaction_on_exception() {
        CurrencyDTO currencyDTO = new CurrencyDTO(2L, "EUR", "Euro", true, 2);
        Currency currency = ApiMapper.INSTANCE.DTOToEntity(currencyDTO);

        when(repository.save(any(Currency.class))).thenReturn(currency);

        // Simulate an exception to trigger rollback
        doThrow(new RuntimeException("Simulated Exception")).when(repository).save(any(Currency.class));

        assertThrows(RuntimeException.class, () -> {
            currencyService.save(currencyDTO);
        });

        // Verify that the save method was called
        verify(repository, times(1)).save(any(Currency.class));

        // Verify that the transaction was rolled back
        Optional<Currency> result = repository.findById(2L);
        assertFalse(result.isPresent());
    }
}