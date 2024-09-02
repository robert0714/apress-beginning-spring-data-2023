package com.apress.catalog.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CurrencyDTOTest {

    @Test
    public void testCurrencyDTOConstructorAndGetters() {
        Long id = 1L;
        String code = "USD";
        String description = "Dollar";
        Boolean enable = true;
        Integer decimalPlaces = 2;

        CurrencyDTO currencyDTO = new CurrencyDTO(id, code, description, enable, decimalPlaces);

        assertEquals(id, currencyDTO.getId());
        assertEquals(code, currencyDTO.getCode());
        assertEquals(description, currencyDTO.getDescription());
        assertEquals(enable, currencyDTO.getEnable());
        assertEquals(decimalPlaces, currencyDTO.getDecimalPlaces());
    }

    @Test
    public void testCurrencyDTOSetters() {
        CurrencyDTO currencyDTO = new CurrencyDTO(1L, "USD", "Dollar", true, 2);

        Long newId = 2L;
        String newCode = "EUR";
        String newDescription = "Euro";
        Boolean newEnable = false;
        Integer newDecimalPlaces = 3;

        currencyDTO.setId(newId);
        currencyDTO.setCode(newCode);
        currencyDTO.setDescription(newDescription);
        currencyDTO.setEnable(newEnable);
        currencyDTO.setDecimalPlaces(newDecimalPlaces);

        assertEquals(newId, currencyDTO.getId());
        assertEquals(newCode, currencyDTO.getCode());
        assertEquals(newDescription, currencyDTO.getDescription());
        assertEquals(newEnable, currencyDTO.getEnable());
        assertEquals(newDecimalPlaces, currencyDTO.getDecimalPlaces());
    }

    @Test
    public void testCurrencyDTOEqualsAndHashCode() {
        CurrencyDTO currencyDTO1 = new CurrencyDTO(1L, "USD", "Dollar", true, 2);
        CurrencyDTO currencyDTO2 = new CurrencyDTO(1L, "USD", "Dollar", true, 2);
        CurrencyDTO currencyDTO3 = new CurrencyDTO(2L, "EUR", "Euro", false, 3);

        assertEquals(currencyDTO1, currencyDTO2);
        assertNotEquals(currencyDTO1, currencyDTO3);
        assertEquals(currencyDTO1.hashCode(), currencyDTO2.hashCode());
        assertNotEquals(currencyDTO1.hashCode(), currencyDTO3.hashCode());
    }

    @Test
    public void testCurrencyDTOToString() {
        CurrencyDTO currencyDTO = new CurrencyDTO(1L, "USD", "Dollar", true, 2);
        String expectedString = "CurrencyDTO{id=1, code='USD', description='Dollar', enable=true, decimalPlaces=2}";

        assertEquals(expectedString, currencyDTO.toString());
    }
}