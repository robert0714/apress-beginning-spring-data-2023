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

	private final CurrencyRepository repository;
	private final Validator validator;
	 
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
		return repository.findById(id)
				.map(ApiMapper.INSTANCE::entityToDTO) ;
	}

	public Mono<CurrencyDTO> save(CurrencyDTO currency) {
		return saveInformationV2(currency);
	}

	@Transactional
	public Mono<CurrencyDTO> update(CurrencyDTO currency) {
		return saveInformationV1(currency);
	}

	public Mono<Void>  delete(Long id) {
		Optional<Currency> currency = repository.findById(id).blockOptional();

		if(currency.isPresent()) {
			currency.get().setEnabled(Boolean.FALSE);
			Mono<Currency> monoObject = repository.save(currency.get());
			return monoObject.then();
		}else{
			Mono<Object> monoObject = Mono.just(currency);  
			return monoObject.then();
		}
	}

	private Mono<CurrencyDTO>  saveInformationV1(CurrencyDTO currency) {
		Currency entity = ApiMapper.INSTANCE.DTOToEntity(currency);

		Set<ConstraintViolation<Currency>> violations = validator.validate(entity);
		if(!violations.isEmpty()) {
			throw new ConstraintViolationException(violations);
		}
		return repository.save(entity)
				.map(ApiMapper.INSTANCE::entityToDTO) ;
	}
	private Mono<CurrencyDTO>  saveInformationV2(CurrencyDTO currency) {		
		return Mono.fromCallable(() -> {
			final Currency entity = ApiMapper.INSTANCE.DTOToEntity(currency);
			Set<ConstraintViolation<Currency>> violations = validator.validate(entity);
			if(!violations.isEmpty()) {
				throw new ConstraintViolationException(violations);
			}
            return entity;
        })
        .flatMap(repository::save)
        .map(ApiMapper.INSTANCE::entityToDTO)
        .onErrorMap(e -> new RuntimeException("Simulated Exception", e));
	}
}
