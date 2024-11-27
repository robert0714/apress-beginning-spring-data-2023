package com.apress.catalog.service;

import com.apress.catalog.dto.CountryDTO;
import com.apress.catalog.dto.CurrencyDTO;
import com.apress.catalog.mapper.ApiMapper;
import com.apress.catalog.model.Country;
import com.apress.catalog.model.Currency;
import com.apress.catalog.repository.CountryRepository;
import com.apress.catalog.repository.CurrencyRepository;

import io.r2dbc.spi.ConnectionFactory;
import reactor.core.publisher.Mono;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest; 
import org.springframework.core.io.ClassPathResource;
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer;
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.utility.DockerImageName;

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
//    		       "logging.level.org.springframework.jdbc.support.JdbcTransactionManager=TRACE",                         		       
//                   "logging.level.org.springframework.data=INFO",
//                   "logging.level.org.springframework.jdbc.core.JdbcTemplate=TRACE" , 
//                   "logging.level.org.springframework.transaction.interceptor=TRACE",
				   "logging.level.org.springframework.orm.jpa=DEBUG",
                   "logging.level.org.springframework.transaction=DEBUG",
                   "logging.level.org.springframework.r2dbc=TRACE",
                   "logging.level.org.springframework.r2dbc=DEBUG",
                   "logging.level.org.springframework.r2dbc.connection=DEBUG"
        })
//@TestPropertySource(properties = "debug=true") 
public class CountryServiceTest {  
	@Autowired
    private CountryRepository countryRepository;

	@Autowired
    private CurrencyRepository currencyRepository; 
    @Autowired
    private CountryService countryService;
    
    @Autowired 
    private ConnectionFactory connectionFactory;
    
    
    @Container
    public static  PostgreSQLContainer<?> postgresql = new  PostgreSQLContainer<>(DockerImageName.parse("postgres:15.6"))
        .withDatabaseName("spring_data")
        .withUsername("test")
        .withPassword("test");
    
    @BeforeEach
   	protected void setUp() throws Exception {
    	postgresql.start();
    	//https://learn.microsoft.com/zh-tw/azure/developer/java/spring-framework/configure-spring-data-r2dbc-with-azure-mysql#create-the-database-schema
        
    	ConnectionFactoryInitializer initializer = new ConnectionFactoryInitializer();
        initializer.setConnectionFactory(connectionFactory);
		ResourceDatabasePopulator populator = new ResourceDatabasePopulator(
				new ClassPathResource("testcontainers/V1.0__init_database.sql")
//				,
//				new ClassPathResource("schema.sql")
				);
        initializer.setDatabasePopulator(populator);
        initializer.afterPropertiesSet();
           
   	} 
    @AfterEach
	public void cleanUp() { 
    	ConnectionFactoryInitializer initializer = new ConnectionFactoryInitializer();
        initializer.setConnectionFactory(connectionFactory);
		ResourceDatabasePopulator populator = new ResourceDatabasePopulator(
				new ClassPathResource("testcontainers/V1.0.2__remove_rows_for_test.sql")
//				,
//				new ClassPathResource("schema.sql")
				);
        initializer.setDatabasePopulator(populator);
        initializer.afterPropertiesSet();
        postgresql.stop();
	}

    
//    @SqlGroup({
//		@Sql(scripts = { "classpath:testcontainers/V1.0__init_database.sql"  
//				},  
//				executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
//		@Sql(scripts = {"classpath:testcontainers/V1.0.2__remove_rows_for_test.sql"},
//				executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
//          })
	@Test   
    @DisplayName("should perform a rollback because PersistenceException")
    public void should_rollback_transaction_on_exception() throws InterruptedException {    	
    	CountryDTO countryDTO = pseudocountryDTO () ; 
    	assertNotNull(countryDTO);
    	
//    	countryService.save(countryDTO);
		assertThrows( Exception.class,				
				() -> countryService.save(countryDTO),				
				"PersistenceException not thrown!");
        
      
        
//		var result = countryService.getById(1L) ;
//        assertAll( "state'enable was not updated to false" ,
//                () -> assertNotNull(result),
//                () -> assertNotEquals(true, result.getEnabled()),
//                () -> assertNotEquals(true, result.getStates().get(0).getEnabled())
//        ); 
		   Mono<Country> result = countryRepository.findById(1L);     
		   
	       assertFalse(result.blockOptional().isPresent()); 
        
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
    			return currencyRepository.findById(1L).blockOptional().get() ;
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