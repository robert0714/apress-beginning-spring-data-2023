package com.apress.catalog;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.data.neo4j.DataNeo4jTest; 
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.Neo4jContainer; 
import org.testcontainers.utility.DockerImageName;
 
/**
 * https://docs.spring.io/spring-data/neo4j/reference/testing/testing-with-spring-boot.html
 * **/
@DataNeo4jTest
class ApiCatalogApplicationTests {
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
	void contextLoads() {
	}

}
