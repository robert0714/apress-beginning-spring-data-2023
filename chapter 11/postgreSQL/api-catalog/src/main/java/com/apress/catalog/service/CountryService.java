package com.apress.catalog.service;

import com.apress.catalog.dto.CountryDTO;
import com.apress.catalog.mapper.ApiMapper;
import com.apress.catalog.model.Country;
import com.apress.catalog.model.State;
import com.apress.catalog.repository.CountryRepository;
import com.apress.catalog.repository.StateRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import reactor.core.publisher.Mono;
 
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class CountryService {

	CountryRepository countryRepository;

	StateRepository stateRepository;

	Validator validator;
  
	public CountryService(CountryRepository countryRepository, StateRepository stateRepository, Validator validator) {
		this.countryRepository = countryRepository;
		this.stateRepository = stateRepository;
		this.validator = validator;
	}

	@Deprecated
	@Transactional(readOnly = true)
	public CountryDTO getById(Long id) {
		CountryDTO response = null; 		
		Optional<Country> country = countryRepository.findById(id).blockOptional();		
		if(country.isPresent()) {
			response = ApiMapper.INSTANCE.entityToDTO(country.get());
		}		
		return response;
	}
	
	@Transactional(readOnly = true)
	public Mono<CountryDTO> getByIdMono(Long id) {
		Mono<CountryDTO> response = countryRepository.findById(id)
									.map(ApiMapper.INSTANCE::entityToDTO) ;
		return response;
	}

	public Mono<CountryDTO> save(CountryDTO currency) {
		return saveInformation(currency);
	}

	public Mono<CountryDTO> update(CountryDTO currency) {
		return saveInformation(currency);
	}

	@Transactional(isolation = Isolation.DEFAULT)
	public void delete(Long id) throws InterruptedException {
		Optional<Country> country = countryRepository.findById(id).blockOptional();
		List<State> states = (List<State>) stateRepository.findAllByCountryId(country.get().getId()).collectList();

		if(country.isPresent()) {
			country.get().setEnabled(Boolean.FALSE);
			countryRepository.save(country.get());

			Thread.sleep(2000L);

			for (State state: states) {
				state.setEnabled(Boolean.FALSE);
				stateRepository.save(state);
			}
		}
	}

	private Mono<CountryDTO> saveInformation( CountryDTO  country ) {
//		Mono<CountryDTO> response = countryMono
//		  .map(ApiMapper.INSTANCE::DTOToEntity)
//		          .flatMap(entity -> {
//		        	  Set<ConstraintViolation<Country>> violations = validator.validate(entity);
//			      	  if(!violations.isEmpty()) {
//			      			throw new ConstraintViolationException(violations);
//			      	  }
//		        	  return countryRepository.save(entity);
//		        })
//		   .map(ApiMapper.INSTANCE::entityToDTO);
		
		Country entity = ApiMapper.INSTANCE.DTOToEntity(country);
		Country savedEntity = countryRepository.save(entity).block();

		Set<ConstraintViolation<Country>> violations = validator.validate(entity);
		if(!violations.isEmpty()) {
			throw new ConstraintViolationException(violations);
		}
		Mono<CountryDTO> response = countryRepository.save(savedEntity)
				.map(ApiMapper.INSTANCE::entityToDTO) ;		
		return response;
	}
}
