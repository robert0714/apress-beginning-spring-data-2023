package com.apress.catalog.mapper;

import com.apress.catalog.dto.AuditDTO;
import com.apress.catalog.dto.CountryDTO;
import com.apress.catalog.dto.CurrencyDTO;
import com.apress.catalog.dto.StateDTO;
import com.apress.catalog.model.Audit;
import com.apress.catalog.model.Country;
import com.apress.catalog.model.Currency;
import com.apress.catalog.model.State;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ApiMapper {

    ApiMapper INSTANCE = Mappers.getMapper( ApiMapper.class );

    // AuditDTO entityToDTO(Audit audit);
    
    // Audit DTOToEntity(AuditDTO audit);
    

    @Mapping(target = "enable", source = "currency.enabled")
    CurrencyDTO entityToDTO(Currency currency);

    @Mapping(target = "enabled", source = "currency.enable")
    Currency DTOToEntity(CurrencyDTO currency);

    CountryDTO entityToDTO(Country country);

    Country DTOToEntity(CountryDTO country);



    @Mapping(target="country", ignore = true)
    StateDTO stateToStateDTO(State state);

    State stateDTOToState(StateDTO state);

    List<CountryDTO> entityToDTO(Iterable<Country> country);
}
