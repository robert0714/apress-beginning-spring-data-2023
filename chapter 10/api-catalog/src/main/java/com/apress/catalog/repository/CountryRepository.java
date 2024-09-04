package com.apress.catalog.repository;

import com.apress.catalog.model.Country;
import com.apress.catalog.model.Currency;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface CountryRepository extends CrudRepository<Country, UUID> {
	List<Country> findByCode(String code);

	// Manual query
	@Query("SELECT c FROM country c where c.code = :code")
	Currency retrieveByCode(@Param("code") String code);
}
