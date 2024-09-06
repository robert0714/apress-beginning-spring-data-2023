package com.apress.catalog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.r2dbc.config.EnableR2dbcAuditing;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

@SpringBootApplication
@EnableR2dbcRepositories(basePackages = "com.apress.catalog")
@EnableR2dbcAuditing
public class ApiCatalogApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiCatalogApplication.class, args);
	}
}
