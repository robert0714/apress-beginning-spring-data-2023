package com.apress.catalog.service;

import com.apress.catalog.dto.CountryDTO; 
 
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;  
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup; 
 

import static org.junit.jupiter.api.Assertions.*; 

/**
 * refer <p>
 * https://github.com/Apress/pro-spring-6/blob/main/chapter09/src/test/java/com/apress/prospring6/nine/AllServiceTest.java<p>
 * https://github.com/Apress/pro-spring-6/blob/main/chapter09-boot/src/test/java/com/apress/prospring6/nine/Chapter9ApplicationTest.java<p>
 * https://github.com/danson-placeholder-service/posts/blob/main/src/test/java/dev/danvega/danson/post/PostControllerIntTest.java<p>
 * https://www.baeldung.com/spring-test-programmatic-transactions<p>
 * **/
@SpringBootTest ( 
        properties = {   
    		       "logging.level.org.springframework.jdbc.support.JdbcTransactionManager=TRACE",                         		       
                   "logging.level.org.springframework.data=INFO",
                   "logging.level.org.springframework.jdbc.core.JdbcTemplate=TRACE" , 
                   "logging.level.org.springframework.transaction.interceptor=TRACE",
				   "logging.level.org.springframework.orm.jpa=DEBUG",
                   "logging.level.org.springframework.transaction=DEBUG",
        })
//@TestPropertySource(properties = "debug=true")
public class CountryServiceTest {  
    @Autowired
    private CountryService countryService;
    

    @SqlGroup({
		@Sql(scripts = { "classpath:testcontainers/V1.0__init_database.sql" ,
				         "classpath:testcontainers/V1.0.1__insert_rows_for_test.sql"   },  
				executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
		@Sql(scripts = {"classpath:testcontainers/V1.0.2__remove_rows_for_test.sql"},
				executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
          })
	@Test   
    @DisplayName("should perform a rollback because PersistenceException")
    public void should_rollback_transaction_on_exception() throws InterruptedException {    	
    	CountryDTO countryDTO = countryService.getById(1L); 
    	assertNotNull(countryDTO);
    	
		assertThrows( Exception.class,				
				() -> {
					countryService.delete(1L);
					CountryDTO countryDTO2 = countryService.getById(1L); 
					countryService.save(countryDTO2);
					},				
				"PersistenceException not thrown!");
        
      
        
		var result = countryService.getById(1L) ;
        assertAll( "state'enable was not updated to false" ,
                () -> assertNotNull(result),
                () -> assertNotEquals(true, result.getEnabled()),
                () -> assertNotEquals(true, result.getStates().get(0).getEnabled())
        ); 
    }
}