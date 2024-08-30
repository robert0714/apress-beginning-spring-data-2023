package com.apress.catalog.mapper;
 
import com.apress.catalog.dto.CurrencyDTO;  
import com.apress.catalog.model.Currency; 
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

public class ApiMapperTest {

    private final ApiMapper apiMapper = Mappers.getMapper(ApiMapper.class);

    @Test
    public void testCurrencyEntityToDTO() {
        Currency currency = new Currency(1L, "USD", "Dollar", true, 2, "$");
        CurrencyDTO currencyDTO = apiMapper.entityToDTO(currency);

        assertNotNull(currencyDTO);
        assertEquals(currency.getId(), currencyDTO.getId());
        assertEquals(currency.getCode(), currencyDTO.getCode());
        assertEquals(currency.getDescription(), currencyDTO.getDescription());
        assertEquals(currency.getEnabled(), currencyDTO.getEnable());
        assertEquals(currency.getDecimalPlaces(), currencyDTO.getDecimalPlaces());
        assertEquals(currency.getSymbol(), currencyDTO.getSymbol());
    }

    @Test
    public void testCurrencyDTOToEntity() {
        CurrencyDTO currencyDTO = new CurrencyDTO(1L, "USD", "Dollar", true, 2);
        Currency currency = apiMapper.DTOToEntity(currencyDTO);

        assertNotNull(currency);
        assertEquals(currencyDTO.getId(), currency.getId());
        assertEquals(currencyDTO.getCode(), currency.getCode());
        assertEquals(currencyDTO.getDescription(), currency.getDescription());
        assertEquals(currencyDTO.getEnable(), currency.getEnabled());
        assertEquals(currencyDTO.getDecimalPlaces(), currency.getDecimalPlaces());
        assertEquals(currencyDTO.getSymbol(), currency.getSymbol());
    }

     
}