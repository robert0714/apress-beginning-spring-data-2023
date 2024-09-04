package com.apress.catalog.repository;

import com.apress.catalog.model.Country;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.cassandra.repository.CassandraRepository;

public interface CustomCountryRepository extends CassandraRepository<Country,UUID>{
    Country save(Country entity);

    Optional<Country> findById(UUID id);

    void deleteById(UUID id);
}