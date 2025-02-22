package com.apress.catalog.service;

import com.apress.catalog.dto.CountryDTO;
import com.apress.catalog.mapper.ApiMapper;
import com.apress.catalog.model.Country;
import com.apress.catalog.model.State;
import com.apress.catalog.repository.CountryRepository;
import com.apress.catalog.repository.StateRepository; 
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
public class CountryService {

	final CountryRepository countryRepository;

	final StateRepository stateRepository;
	final Validator validator;

	 
	public CountryService(CountryRepository countryRepository, StateRepository stateRepository,
						  Validator validator) {
		this.countryRepository = countryRepository;
		this.stateRepository = stateRepository;
		this.validator = validator;
	}

	@Transactional(readOnly = true)
	public CountryDTO getById(UUID id) {
		CountryDTO response = null;
		Optional<Country> country = countryRepository.findById(id);
		
		if(country.isPresent()) {
			response = ApiMapper.INSTANCE.entityToDTO(country.get());
		}
		
		return response;
	}

	public CountryDTO save(CountryDTO currency) {
		return saveInformation(currency);
	}

	public CountryDTO update(CountryDTO currency) {
		return saveInformation(currency);
	}

	@Transactional(isolation = Isolation.DEFAULT)
	public void delete(UUID id) throws InterruptedException {
		Optional<Country> country = countryRepository.findById(id);
		List<State> states = stateRepository.findAllByCountryId(country.get().getId());

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

	private CountryDTO saveInformation(CountryDTO country) {
		Country entity = ApiMapper.INSTANCE.DTOToEntity(country);
		Country savedEntity = countryRepository.save(entity);

		Set<ConstraintViolation<Country>> violations = validator.validate(entity);
		if(!violations.isEmpty()) {
			throw new ConstraintViolationException(violations);
		}

		return ApiMapper.INSTANCE.entityToDTO(savedEntity);
	}
}
