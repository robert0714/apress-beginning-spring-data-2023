package com.apress.catalog.config;
 
  
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer; 
  

@TestConfiguration
public class TestMongoDBApplication {
  public static MongoDBContainer container ; 
  @Bean 
  @ServiceConnection
  public MongoDBContainer  mongoDBContainer() {
	 this.container = new MongoDBContainer ("mongo:4.0.10")
//			 .withEnv("EXTRA_OPTS", "\"--lower_case_table_names=1\"")
			 .withEnv("TZ", "Asia/Taipei") 
//			 .withPassword("") 
			 ;
  	 return container;
  } 
  @DynamicPropertySource
  private static void registerRedisProperties(DynamicPropertyRegistry registry) {  
      registry.add("spring.data.mongodb.host", () ->container.getHost());
      registry.add("pring.data.mongodb.port", () -> container.getFirstMappedPort().toString()); 
      registry.add("spring.data.mongodb.database", () ->  "catalog");   
  }
		 
}
