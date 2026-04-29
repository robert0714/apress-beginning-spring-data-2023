package com.apress.catalog.repository;

import com.apress.catalog.model.Country;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.ComposeContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.File;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
@SpringBootTest
public class CountryRepositoryDockerComposeIntegrationTest {

    private static final Integer POSTGRES_PORT = 5432;
    @Autowired
    CountryRepository countryRepository;


    private static final ComposeContainer environment =
            new ComposeContainer(new File("src/test/resources/docker-compose.yml"))
                    .withExposedService("postgres", POSTGRES_PORT, Wait.forListeningPort());

    @BeforeAll
    public static void setUp() {
        environment.start();
    }

    @AfterAll
    public static void tearDown() {
        environment.stop();
    }

    @DynamicPropertySource
    static void postgresqlProperties(DynamicPropertyRegistry registry) {
        String postgresUrl = environment.getServiceHost("postgres", POSTGRES_PORT)
                + ":" + environment.getServicePort("postgres", POSTGRES_PORT);

        registry.add("spring.datasource.url", () -> "jdbc:postgresql://" + postgresUrl + "/catalog");
        registry.add("spring.datasource.username", () -> "postgres");
        registry.add("spring.datasource.password", () -> "postgres");
    }

    @Test
    public void should_get_a_country() {
        Optional<Country> country = countryRepository.findById(1L);

        assertAll(
                ()-> assertTrue(country.isPresent()),
                ()-> assertEquals("AR", country.get().getCode())
        );
    }
}