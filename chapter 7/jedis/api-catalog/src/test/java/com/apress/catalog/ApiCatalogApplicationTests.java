package com.apress.catalog;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.utility.DockerImageName;

@SpringBootTest
class ApiCatalogApplicationTests {
	static {
        GenericContainer<?> redis = new GenericContainer<>(DockerImageName.parse("redis:6.2.6-alpine"))
          .withExposedPorts(6379);
        redis.start();
        System.setProperty("spring.redis.host", redis.getHost());
        System.setProperty("spring.redis.port", redis.getMappedPort(6379).toString());
    }
	@Test
	void contextLoads() {
	}

}
