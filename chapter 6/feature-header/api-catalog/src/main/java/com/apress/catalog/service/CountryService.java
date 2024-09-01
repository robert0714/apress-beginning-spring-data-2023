package com.apress.catalog.service;

import com.apress.catalog.dto.CountryDTO; 
 
public interface CountryService { 
	public CountryDTO getById(Long id, Boolean newImplementation)  ;

	public CountryDTO save(CountryDTO currency) ;
	public CountryDTO update(CountryDTO currency) ;
	public void delete(Long id) throws InterruptedException ;

}
