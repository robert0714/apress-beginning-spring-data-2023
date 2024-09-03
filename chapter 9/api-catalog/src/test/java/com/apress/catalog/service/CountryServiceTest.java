package com.apress.catalog.service;
  
import com.apress.catalog.dto.CountryDTO;
import com.apress.catalog.dto.CurrencyDTO;
import com.apress.catalog.mapper.ApiMapper;
import com.apress.catalog.model.Country;
import com.apress.catalog.model.Currency;
import com.apress.catalog.repository.CountryRepository;
import com.apress.catalog.repository.CurrencyRepository;

import org.junit.jupiter.api.AfterAll; 
import org.junit.jupiter.api.BeforeAll; 
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.neo4j.DataNeo4jTest;
import org.springframework.boot.test.context.SpringBootTest; 
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.Neo4jContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional; 

/**
 * refer <p>
 * https://github.com/Apress/pro-spring-6/blob/main/chapter09/src/test/java/com/apress/prospring6/nine/AllServiceTest.java<p>
 * https://github.com/Apress/pro-spring-6/blob/main/chapter09-boot/src/test/java/com/apress/prospring6/nine/Chapter9ApplicationTest.java<p>
 * https://github.com/danson-placeholder-service/posts/blob/main/src/test/java/dev/danvega/danson/post/PostControllerIntTest.java<p>
 * https://www.baeldung.com/spring-test-programmatic-transactions<p>
 * https://docs.spring.io/spring-data/neo4j/reference/testing/testing-with-spring-boot.html<p>
 * 
 * **/
//@DataNeo4jTest ( 
@SpringBootTest(
		properties = {   
// 		       "logging.level.org.springframework.jdbc.support.JdbcTransactionManager=TRACE",                         		       
//                "logging.level.org.springframework.data=INFO",
//                "logging.level.org.springframework.jdbc.core.JdbcTemplate=TRACE" , 
//                "logging.level.org.springframework.transaction.interceptor=TRACE",
				   "logging.level.org.springframework.orm.jpa=DEBUG",
                "logging.level.org.springframework.transaction=DEBUG",
        } )
//@TestPropertySource(properties = "debug=true") 
@Testcontainers(disabledWithoutDocker = true)
public class CountryServiceTest {  
	@Autowired
    private CountryRepository countryRepository;

	@Autowired
    private CurrencyRepository currencyRepository; 
    @Autowired
    private CountryService countryService;
    
    private static Neo4jContainer<?> neo4jContainer;
    @BeforeAll
	static void initializeNeo4j() { 
		neo4jContainer = new Neo4jContainer<>(DockerImageName.parse("neo4j:4.4"))
			.withEnv("TZ", "Asia/Taipei") 
			.withAdminPassword("somePassword");
		neo4jContainer.start();
	}

	@AfterAll
	static void stopNeo4j() {

		neo4jContainer.close();
	}
	@DynamicPropertySource
	static void neo4jProperties(DynamicPropertyRegistry registry) {

		registry.add("spring.neo4j.uri", neo4jContainer::getBoltUrl);
		registry.add("spring.neo4j.authentication.username", () -> "neo4j");
		registry.add("spring.neo4j.authentication.password", neo4jContainer::getAdminPassword);
	}
    
	@Test   
    @DisplayName("should perform a rollback because PersistenceException")
    public void should_rollback_transaction_on_exception() throws InterruptedException {    	
    	CountryDTO countryDTO = pseudocountryDTO () ; 
    	assertNotNull(countryDTO);
    	
    	countryService.save(countryDTO);
//		assertThrows( Exception.class,				
//				() -> countryService.save(countryDTO),				
//				"PersistenceException not thrown!");
    
		 Optional<Country> result = countryRepository.findById(1L);     

	     assertFalse(result.isPresent()); 
        
    }
    protected CountryDTO pseudocountryDTO () {
    	CountryDTO countryDTO = new CountryDTO();
        countryDTO.setEnabled(Boolean.TRUE);
        countryDTO.setCode("ARG");
        countryDTO.setName("Argentina");
        countryDTO.setLocale("Tawain");
        countryDTO.setTimezone("zh_TW");

//        Currency currency = scenarioBy(1); 
        Currency currency = scenarioBy(0);


//        StateDTO stateDTO = new StateDTO(1L, "CA", "California", true, countryDTO);
//        countryDTO.setStates(List.of(stateDTO));

        CurrencyDTO currencyDTO = ApiMapper.INSTANCE.entityToDTO(currency );
        countryDTO.setCurrency(currencyDTO); 
        return countryDTO ;
    } 
    protected Currency scenarioBy(int scenario) {
    	switch(scenario) {
    		case 0:
    			return dataIntegrityViolationCurrency() ;
    		default:
    			return currencyRepository.findById(1L).get() ;
    	}
    }
    protected Currency dataIntegrityViolationCurrency() {
    	Currency currency = new Currency( ); 
        currency.setCode("ARS");
        currency.setDescription("Peso argentino");
        currency.setEnabled(true);
        currency.setDecimalPlaces(2);
        currency.setSymbol("$");  
        return currency;
    }
    
}