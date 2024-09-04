package com.apress.catalog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication; 
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;
//@EnableCassandraRepositories(basePackages = "com.apress.catalog.repository")
@SpringBootApplication
public class ApiCatalogApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiCatalogApplication.class, args);
	}
}
