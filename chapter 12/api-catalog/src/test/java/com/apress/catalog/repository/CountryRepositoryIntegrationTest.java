package com.apress.catalog.repository;

import com.apress.catalog.model.Country;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.output.OutputFrame;
import org.testcontainers.containers.output.ToStringConsumer;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

//This annotation enable the support of Testcontainers
@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CountryRepositoryIntegrationTest {

    @Autowired
    CountryRepository countryRepository;

    //Define the container thar you will use
    public static PostgreSQLContainer postgreSQL =
            new PostgreSQLContainer<>("postgres:15.6")
                    .withUsername("postgres")
                    .withPassword("postgres")
                    .withDatabaseName("catalog")
                    .withReuse(true);

    //This class have the responsibility to obtain all the logs from the container
    public static ToStringConsumer consumer = new ToStringConsumer();

    //Contain all the definition to send the logs from the container to the consumer
    @BeforeAll
    public static void setUp() {
        postgreSQL.start();

        postgreSQL.followOutput(consumer,
                OutputFrame.OutputType.STDOUT,
                OutputFrame.OutputType.STDERR);
    }

    //Take the logs that have the consumer and write a file
    @AfterAll
    public static void tearDown() throws IOException {
        Path log = Path.of("./logs.log");
        byte[] bytes = consumer.toUtf8String().getBytes();
        Files.write(log, bytes, StandardOpenOption.CREATE);
        //postgreSQL.stop();
    }

    //Override the configuration of Spring Boot to use the container
    @DynamicPropertySource
    static void postgresqlProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSQL::getJdbcUrl);
        registry.add("spring.datasource.username", postgreSQL::getUsername);
        registry.add("spring.datasource.password", postgreSQL::getPassword);
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