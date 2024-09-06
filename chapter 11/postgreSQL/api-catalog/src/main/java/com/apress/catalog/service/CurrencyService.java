package com.apress.catalog.service;
 
import com.apress.catalog.dto.CurrencyDTO;
import com.apress.catalog.mapper.ApiMapper;
import com.apress.catalog.model.Currency;
import com.apress.catalog.repository.CurrencyRepository; 
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import reactor.core.publisher.Mono;

import java.util.Optional;
import java.util.Set;

@Service
public class CurrencyService {

	CurrencyRepository repository;
	Validator validator;
	 
	public CurrencyService(CurrencyRepository repository, Validator validator) {
		this.repository = repository;
		this.validator = validator;
	}
	@Deprecated
	public CurrencyDTO getById(Long id) {
		CurrencyDTO response = null;
		Optional<Currency> currency = repository.findById(id).blockOptional();
		
		if(currency.isPresent()) {
			response = ApiMapper.INSTANCE.entityToDTO(currency.get());;
		}
		
		return response;
	}
	public Mono<CurrencyDTO> getByIdMono(Long id) {
		Mono<CurrencyDTO> response = repository.findById(id)
				.map(ApiMapper.INSTANCE::entityToDTO) ;
		return response;
	}

	public Mono<CurrencyDTO> save(CurrencyDTO currency) {
		return saveInformation(currency);
	}

	@Transactional
	public Mono<CurrencyDTO> update(CurrencyDTO currency) {
		return saveInformation(currency);
	}

	public void delete(Long id) {
		Optional<Currency> currency = repository.findById(id).blockOptional();

		if(currency.isPresent()) {
			currency.get().setEnabled(Boolean.FALSE);
			repository.save(currency.get());
		}
	}

	private Mono<CurrencyDTO>  saveInformation(CurrencyDTO currency) {
		Currency entity = ApiMapper.INSTANCE.DTOToEntity(currency);

		Set<ConstraintViolation<Currency>> violations = validator.validate(entity);
		if(!violations.isEmpty()) {
			throw new ConstraintViolationException(violations);
		}
		Mono<CurrencyDTO> response = repository.save(entity)
				.map(ApiMapper.INSTANCE::entityToDTO) ;
		return response;
	}
}
