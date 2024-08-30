package com.apress.catalog.mapper;

import com.apress.catalog.dto.CurrencyDTO;
import com.apress.catalog.model.Currency;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ApiMapper {

    ApiMapper INSTANCE = Mappers.getMapper( ApiMapper.class );

    @Mapping(target = "enable", source = "currency.enabled")
    CurrencyDTO entityToDTO(Currency currency);

    @Mapping(target = "enabled", source = "currency.enable")
    Currency DTOToEntity(CurrencyDTO currency);
}
