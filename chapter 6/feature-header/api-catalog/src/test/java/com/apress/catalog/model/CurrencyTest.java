package com.apress.catalog.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CurrencyTest {

    @Test
    public void testCurrencyConstructorAndGetters() {
        Long id = 1L;
        String code = "USD";
        String description = "Dollar";
        Boolean enabled = true;
        int decimalPlaces = 2;
        String symbol = "$";

        Currency currency = new Currency(id, code, description, enabled, decimalPlaces, symbol);

        assertEquals(id, currency.getId());
        assertEquals(code, currency.getCode());
        assertEquals(description, currency.getDescription());
        assertEquals(enabled, currency.getEnabled());
        assertEquals(decimalPlaces, currency.getDecimalPlaces());
        assertEquals(symbol, currency.getSymbol());
    }

    @Test
    public void testCurrencySetters() {
        Currency currency = new Currency(1L, "USD", "Dollar", true, 2, "$");

        Long newId = 2L;
        String newCode = "EUR";
        String newDescription = "Euro";
        Boolean newEnabled = false;
        int newDecimalPlaces = 3;
        String newSymbol = "€";

        currency.setId(newId);
        currency.setCode(newCode);
        currency.setDescription(newDescription);
        currency.setEnabled(newEnabled);
        currency.setDecimalPlaces(newDecimalPlaces);
        currency.setSymbol(newSymbol);

        assertEquals(newId, currency.getId());
        assertEquals(newCode, currency.getCode());
        assertEquals(newDescription, currency.getDescription());
        assertEquals(newEnabled, currency.getEnabled());
        assertEquals(newDecimalPlaces, currency.getDecimalPlaces());
        assertEquals(newSymbol, currency.getSymbol());
    }

    @Test
    public void testCurrencyEqualsAndHashCode() {
        Currency currency1 = new Currency(1L, "USD", "Dollar", true, 2, "$");
        Currency currency2 = new Currency(1L, "USD", "Dollar", true, 2, "$");
        Currency currency3 = new Currency(2L, "EUR", "Euro", false, 3, "€");

        assertEquals(currency1, currency2);
        assertNotEquals(currency1, currency3);
        assertEquals(currency1.hashCode(), currency2.hashCode());
        assertNotEquals(currency1.hashCode(), currency3.hashCode());
    }

    @Test
    public void testCurrencyToString() {
        Currency currency = new Currency(1L, "USD", "Dollar", true, 2, "$");
        String expectedString = "Currency{id=1, code='USD', description='Dollar', enabled=true, decimalPlaces=2, symbol='$'}";

        assertEquals(expectedString, currency.toString());
    }
}