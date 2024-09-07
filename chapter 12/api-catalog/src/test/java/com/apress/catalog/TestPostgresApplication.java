package com.apress.catalog;


import org.springframework.boot.SpringApplication;  
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer; 
 
 
  

@TestConfiguration
public class TestPostgresApplication {
  public static PostgreSQLContainer<?>  container ; 
  
  @Bean 
  @ServiceConnection
  public PostgreSQLContainer<?> mySQLContainer() {
	 this.container = 
  	  new PostgreSQLContainer<>("postgres:15.6")
//  			 .withUsername("root")
		  	 .withUsername("postgres")
		     .withPassword("postgres")
		     .withDatabaseName("catalog")
  			 .withEnv("EXTRA_OPTS", "\"--lower_case_table_names=1\"")
  			 .withEnv("TZ", "Asia/Taipei")
//  			 .withPassword("") 
  			 ;
	 return this.container;
  } 
  @DynamicPropertySource
  static void registerProperties(DynamicPropertyRegistry registry) {
		registry.add("spring.datasource.url", container::getJdbcUrl);
		registry.add("spring.datasource.url", () -> container.getJdbcUrl());
		registry.add("spring.datasource.username", () -> container.getUsername());
		registry.add("spring.datasource.password", () -> container.getPassword());
		registry.add("spring.jpa.hibernate.ddl-auto", () -> "create");
  }
  public static void main(String[] args) {
		SpringApplication.from(ApiCatalogApplication::main)
	  		.with(TestPostgresApplication.class)
	  		.run(args);
	}
		 
}
