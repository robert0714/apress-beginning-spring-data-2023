package com.apress.catalog;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.utility.DockerImageName;
import com.redis.testcontainers.RedisContainer;

@SpringBootTest
class ApiCatalogApplicationTests {

	static final RedisContainer REDIS_CONTAINER;

	static {
		REDIS_CONTAINER = new RedisContainer(DockerImageName.parse("redis:6.2.14-alpine")).withExposedPorts(6379);
		REDIS_CONTAINER.start();
	}

	@DynamicPropertySource
	static void registerRedisProperties(DynamicPropertyRegistry registry) {
		registry.add("redis.master.host", REDIS_CONTAINER::getHost);
		registry.add("redis.master.port", () -> REDIS_CONTAINER.getMappedPort(6379).toString());
		registry.add("redis.slaves[0].host", REDIS_CONTAINER::getHost);
		registry.add("redis.slaves[0].port", () -> REDIS_CONTAINER.getMappedPort(6379).toString());
		registry.add("spring.data.redis.host", REDIS_CONTAINER::getHost);
		registry.add("spring.data.redis.port", () -> REDIS_CONTAINER.getMappedPort(6379).toString());
	}

	@Test
	void contextLoads() {
	}

}
