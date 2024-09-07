package com.apress.catalog.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class StateDTOTest {

    @Test
    public void testStateDTOConstructorAndGetters() {
    	CurrencyDTO currency = new CurrencyDTO();
        CountryDTO countryDTO = new CountryDTO(1L, "US", "United States", "en_US", "America/New_York", true, currency);
        
        StateDTO stateDTO = new StateDTO(1L, "CA", "California", true, countryDTO);

        assertEquals(1L, stateDTO.getId());
        assertEquals("CA", stateDTO.getCode());
        assertEquals("California", stateDTO.getName());
        assertTrue(stateDTO.getEnabled());
        assertEquals(countryDTO, stateDTO.getCountry());
    }

    @Test
    public void testStateDTOSetters() {
        StateDTO stateDTO = new StateDTO();
        CurrencyDTO currency = new CurrencyDTO();
        CountryDTO countryDTO = new CountryDTO(1L, "US", "United States", "en_US", "America/New_York", true, currency);
       
        stateDTO.setId(1L);
        stateDTO.setCode("CA");
        stateDTO.setName("California");
        stateDTO.setEnabled(true);
        stateDTO.setCountry(countryDTO);

        assertEquals(1L, stateDTO.getId());
        assertEquals("CA", stateDTO.getCode());
        assertEquals("California", stateDTO.getName());
        assertTrue(stateDTO.getEnabled());
        assertEquals(countryDTO, stateDTO.getCountry());
    }

    @Test
    public void testStateDTOEqualsAndHashCode() {
    	CurrencyDTO currency = new CurrencyDTO();
        CountryDTO countryDTO1 = new CountryDTO(1L, "US", "United States", "en_US", "America/New_York", true, currency);
        CountryDTO countryDTO2 = new CountryDTO(2L, "CA", "Canada", "ca_CAN", "Canada/Yukon", true, currency);

        StateDTO stateDTO1 = new StateDTO(1L, "CA", "California", true, countryDTO1);
        StateDTO stateDTO2 = new StateDTO(1L, "CA", "California", true, countryDTO1);
        StateDTO stateDTO3 = new StateDTO(2L, "NY", "New York", true, countryDTO2);

        assertEquals(stateDTO1, stateDTO2);
        assertNotEquals(stateDTO1, stateDTO3);
        assertEquals(stateDTO1.hashCode(), stateDTO2.hashCode());
        assertNotEquals(stateDTO1.hashCode(), stateDTO3.hashCode());
    }
}