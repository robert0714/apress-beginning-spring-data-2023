package com.apress.catalog.mapper; 

import com.apress.catalog.dto.AuditDTO;
import com.apress.catalog.dto.CountryDTO;
import com.apress.catalog.dto.CurrencyDTO;
import com.apress.catalog.dto.StateDTO;
import com.apress.catalog.model.Audit;
import com.apress.catalog.model.Country;
import com.apress.catalog.model.Currency;
import com.apress.catalog.model.State;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

public class ApiMapperTest {

    private final ApiMapper apiMapper = Mappers.getMapper(ApiMapper.class);

     
    @Test
    public void testCurrencyEntityToDTO() {
        Currency currency = new Currency();
        currency.setId(1L);
        currency.setCode("USD");
        currency.setDescription("Dollar");
        currency.setEnabled(true);
        currency.setDecimalPlaces(2);
        currency.setSymbol("$");

        CurrencyDTO currencyDTO = apiMapper.entityToDTO(currency);

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
 
//    @Test
//    public void testAuditEntityToDTO() {
//        Audit audit = new Audit(); 
//        audit.setCreatedOn(LocalDateTime.of(2023, 10, 1, 8, 0) );
//        audit.setUpdatedOn(LocalDateTime.of(2023, 10, 1, 8, 0) );
//
//        AuditDTO auditDTO = apiMapper.entityToDTO(audit);
// 
//        assertEquals(audit.getCreatedOn(), auditDTO.getCreatedOn());
//        assertEquals(audit.getUpdatedOn(), auditDTO.getUpdatedOn());
//    }
//
//    @Test
//    public void testAuditDTOToEntity() {
//        AuditDTO auditDTO = new AuditDTO(); 
//        auditDTO.setCreatedOn(LocalDateTime.of(2023, 10, 1, 8, 0) );
//        auditDTO.setUpdatedOn(LocalDateTime.of(2023, 10, 1, 8, 0) );
//
//        Audit audit = apiMapper.DTOToEntity(auditDTO);
// 
//        assertEquals(auditDTO.getCreatedOn(), audit.getCreatedOn());
//        assertEquals(auditDTO.getUpdatedOn(), audit.getUpdatedOn());
//    }

    

    @Test
    public void testCountryEntityToDTO() {
        Country country = new Country();
        country.setId(1L);
        country.setCode("US");
        country.setName("United States");
        country.setEnabled(true);

        CountryDTO countryDTO = apiMapper.entityToDTO(country);

        assertEquals(country.getId(), countryDTO.getId());
        assertEquals(country.getCode(), countryDTO.getCode());
        assertEquals(country.getName(), countryDTO.getName());
        assertEquals(country.getEnabled(), countryDTO.getEnabled());
    }

    @Test
    public void testCountryDTOToEntity() {
        CountryDTO countryDTO = new CountryDTO();
        countryDTO.setId(1L);
        countryDTO.setCode("US");
        countryDTO.setName("United States");

        Country country = apiMapper.DTOToEntity(countryDTO);

        assertEquals(countryDTO.getId(), country.getId());
        assertEquals(countryDTO.getCode(), country.getCode());
        assertEquals(countryDTO.getName(), country.getName());
    }

    @Test
    public void testStateEntityToDTO() {
        State state = new State();
        state.setId(1L);
        state.setCode("CA");
        state.setName("California");

        StateDTO stateDTO = apiMapper.stateToStateDTO(state);

        assertEquals(state.getId(), stateDTO.getId());
        assertEquals(state.getCode(), stateDTO.getCode());
        assertEquals(state.getName(), stateDTO.getName());
    }

    @Test
    public void testStateDTOToEntity() {
        StateDTO stateDTO = new StateDTO();
        stateDTO.setId(1L);
        stateDTO.setCode("CA");
        stateDTO.setName("California");

        State state = apiMapper.stateDTOToState(stateDTO);

        assertEquals(stateDTO.getId(), state.getId());
        assertEquals(stateDTO.getCode(), state.getCode());
        assertEquals(stateDTO.getName(), state.getName());
    }
}