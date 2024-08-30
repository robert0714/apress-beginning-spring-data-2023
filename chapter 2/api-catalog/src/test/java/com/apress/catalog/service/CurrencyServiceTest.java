package com.apress.catalog.service;

import com.apress.catalog.dto.CurrencyDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CurrencyServiceTest {
    private CurrencyService currencyService;

    @BeforeEach
    public void setUp() {
        currencyService = new CurrencyService();
    }

    @Test
    public void should_get_currency_by_id() {
        CurrencyDTO currency = currencyService.getById(1L);
        assertNotNull(currency);
        assertEquals(1L, currency.getId());
        assertEquals("USD", currency.getCode());
        assertEquals("Dollar", currency.getDescription());
        assertTrue(currency.getEnable());
        assertEquals(2, currency.getDecimalPlaces());
    }

    @Test
    public void should_save_currency() {
        CurrencyDTO currencyDTO = new CurrencyDTO(2L, "EUR", "Euro", true, 2);
        CurrencyDTO savedCurrency = currencyService.save(currencyDTO);
        assertNotNull(savedCurrency);
        assertEquals(currencyDTO, savedCurrency);
    }

    @Test
    public void should_update_currency() {
        CurrencyDTO currencyDTO = new CurrencyDTO(3L, "GBP", "Pound", true, 2);
        CurrencyDTO updatedCurrency = currencyService.update(currencyDTO);
        assertNotNull(updatedCurrency);
        assertEquals(currencyDTO, updatedCurrency);
    }

    @Test
    public void should_delete_currency() {
        currencyService.delete(4L);
        // Verify that the delete method does not throw any exception
        assertDoesNotThrow(() -> currencyService.delete(4L));
    }
}