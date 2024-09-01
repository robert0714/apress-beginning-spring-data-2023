package com.apress.catalog.dto;

import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

public class CountryDTOTest {

    @Test
    public void testCountryDTO() {
        CurrencyDTO currency = new CurrencyDTO();
        StateDTO state = new StateDTO();
        CountryDTO country = new CountryDTO(1L, "US", "United States", "en_US", "America/New_York", true, currency);
        country.setStates(Collections.singletonList(state));

        assertEquals(1L, country.getId());
        assertEquals("US", country.getCode());
        assertEquals("United States", country.getName());
        assertEquals("en_US", country.getLocale());
        assertEquals("America/New_York", country.getTimezone());
        assertTrue(country.getEnabled());
        assertEquals(currency, country.getCurrency());
        assertEquals(1, country.getStates().size());
        assertEquals(state, country.getStates().get(0));
    }
    @Test
    public void testCountryDTOSetters() {
        CurrencyDTO currency = new CurrencyDTO(2L, "CAD", "Canadian Dollar", true, 2);
        StateDTO state = new StateDTO(2L, "ON", "Ontario", true, null);
        CountryDTO country = new CountryDTO();
        country.setId(2L);
        country.setCode("CA");
        country.setName("Canada");
        country.setLocale("en_CA");
        country.setTimezone("America/Toronto");
        country.setEnabled(false);
        country.setCurrency(currency);
        country.setStates(Collections.singletonList(state));
        state.setCountry(country);

        assertEquals(2L, country.getId());
        assertEquals("CA", country.getCode());
        assertEquals("Canada", country.getName());
        assertEquals("en_CA", country.getLocale());
        assertEquals("America/Toronto", country.getTimezone());
        assertFalse(country.getEnabled());
        assertEquals(currency, country.getCurrency());
        assertEquals(1, country.getStates().size());
        assertEquals(state, country.getStates().get(0));
        assertEquals(country, state.getCountry());
    } 
    @Test
    public void testCountryDTOConstructorAndGetters() {
        CurrencyDTO currency = new CurrencyDTO(1L, "USD", "United States Dollar", true, 2);
        StateDTO state = new StateDTO(1L, "CA", "California", true, null);
        CountryDTO country = new CountryDTO(1L, "US", "United States", "en_US", "America/New_York", true, currency);
        country.setStates(Collections.singletonList(state));
        state.setCountry(country);

        assertEquals(1L, country.getId());
        assertEquals("US", country.getCode());
        assertEquals("United States", country.getName());
        assertEquals("en_US", country.getLocale());
        assertEquals("America/New_York", country.getTimezone());
        assertTrue(country.getEnabled());
        assertEquals(currency, country.getCurrency());
        assertEquals(1, country.getStates().size());
        assertEquals(state, country.getStates().get(0));
        assertEquals(country, state.getCountry());
    }

    

    @Test
    public void testCountryDTOEqualsAndHashCode() {
        CurrencyDTO currency1 = new CurrencyDTO(1L, "USD", "United States Dollar", true, 2);
        CurrencyDTO currency2 = new CurrencyDTO(2L, "CAD", "Canadian Dollar", true, 2);
        StateDTO state1 = new StateDTO(1L, "CA", "California", true, null);
        StateDTO state2 = new StateDTO(2L, "ON", "Ontario", true, null);

        CountryDTO country1 = new CountryDTO(1L, "US", "United States", "en_US", "America/New_York", true, currency1);
        CountryDTO country2 = new CountryDTO(1L, "US", "United States", "en_US", "America/New_York", true, currency1);
        CountryDTO country3 = new CountryDTO(2L, "CA", "Canada", "en_CA", "America/Toronto", false, currency2);

        country1.setStates(Collections.singletonList(state1));
        country2.setStates(Collections.singletonList(state1));
        country3.setStates(Collections.singletonList(state2));

        state1.setCountry(country1);
        state2.setCountry(country3);

        assertEquals(country1, country2);
        assertNotEquals(country1, country3);
        assertEquals(country1.hashCode(), country2.hashCode());
        assertNotEquals(country1.hashCode(), country3.hashCode());
    }
}