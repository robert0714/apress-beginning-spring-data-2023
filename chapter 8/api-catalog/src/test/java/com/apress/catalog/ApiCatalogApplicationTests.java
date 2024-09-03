package com.apress.catalog;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;  
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.utility.DockerImageName;
 

@SpringBootTest  
class ApiCatalogApplicationTests {
	static {
		MongoDBContainer container = new MongoDBContainer(DockerImageName.parse("mongo:4.0.10"))
        		.withEnv("TZ", "Asia/Taipei") ;
        container.start();
        System.setProperty("spring.data.mongodb.host", container.getHost());
        System.setProperty("spring.data.mongodb.port", container.getFirstMappedPort().toString()); 
    }
	@Test
	void contextLoads() {
	}

}
