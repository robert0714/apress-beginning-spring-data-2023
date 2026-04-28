package com.apress.catalog.repository;

import com.apress.catalog.model.Currency;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.postgresql.PostgreSQLContainer;
import org.testcontainers.containers.output.ToStringConsumer;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CurrencyRepositoryIntegrationTest {

    @Autowired
    CurrencyRepository currencyRepository;

    public static PostgreSQLContainer postgreSQL =
            new PostgreSQLContainer("postgres:14")
                    .withUsername("postgres")
                    .withPassword("postgres")
                    .withDatabaseName("catalog")
                    .withReuse(true);

    public static ToStringConsumer consumer = new ToStringConsumer();

    @BeforeAll
    public static void setUp() {
        postgreSQL.start();
    }
    @AfterAll
    static void stopContainer() {
    	postgreSQL.stop(); // Stop the container after all tests
    }

    @DynamicPropertySource
    static void postgresqlProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSQL::getJdbcUrl);
        registry.add("spring.datasource.username", postgreSQL::getUsername);
        registry.add("spring.datasource.password", postgreSQL::getPassword);
    }

    @Test
    public void should_get_a_currency() {
        Optional<Currency> currency = currencyRepository.findById(1L);

        assertAll(
                ()-> assertTrue(currency.isPresent()),
                ()-> assertEquals("ARS", currency.get().getCode())
        );
    }


    @Test
    public void should_get_all_currencies() {
        List<Currency> currencies = (List<Currency>) currencyRepository.findAll();

        assertAll(
                ()-> assertNotNull(currencies),
                ()-> assertEquals(22, currencies.size())
        );
    }
}