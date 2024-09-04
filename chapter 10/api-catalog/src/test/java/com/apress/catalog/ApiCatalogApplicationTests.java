package com.apress.catalog;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource; 
import org.testcontainers.containers.CassandraContainer;
import org.testcontainers.junit.jupiter.Container; 
import org.testcontainers.utility.DockerImageName;

import com.datastax.driver.core.Cluster;
 

/**
 * refer <p> 
 * https://github.com/eugenp/tutorials/blob/master/persistence-modules/spring-data-cassandra-2/src/test/java/org/baeldung/springcassandra/CassandraNestedLiveTest.java<p>
 * **/
@SpringBootTest
class ApiCatalogApplicationTests {
	private static final String KEYSPACE_NAME = "twa";
    @Container
    private static final CassandraContainer<?> cassandra 
        = new CassandraContainer<>(DockerImageName.parse("cassandra:4.0.13")) 
           .withEnv("TZ", "Asia/Taipei")
    		.withExposedPorts(9042);
    
    @DynamicPropertySource
    private static void registerRedisProperties(DynamicPropertyRegistry registry) {  
        registry.add("spring.cassandra.keyspace-name", () ->KEYSPACE_NAME);
        registry.add("spring.cassandra.schema-action", () -> "recreate"); 
        registry.add("spring.cassandra.local-datacenter", () -> cassandra.getLocalDatacenter()); 
        registry.add("spring.cassandra.contact-points", () -> "localhost:"+String.valueOf(cassandra.getMappedPort(9042)));
        registry.add("spring.cassandra.port", () -> String.valueOf(cassandra.getMappedPort(9042))); 
    }
    @BeforeAll
    public static void setUpBeforeAll() {
    	cassandra.start();
    	final Cluster.Builder builder = Cluster
                .builder()
                .addContactPoint(cassandra.getHost())
                .withPort(cassandra.getMappedPort(9042));
    	builder.withoutJMXReporting();
    	createKeyspace(builder.build());
    }
    @BeforeEach
	protected void setUp() throws Exception {
    	assertThat(cassandra.isRunning()).isTrue(); 
        
	}
    @AfterAll
    public static void tearDown() {
    	cassandra.stop();
    }

    static void createKeyspace(com.datastax.driver.core.Cluster cluster) {
        try(com.datastax.driver.core.Session session = cluster.connect()) {
            session.execute("CREATE KEYSPACE IF NOT EXISTS " + KEYSPACE_NAME + " WITH replication = \n" +
              "{'class':'SimpleStrategy','replication_factor':'1'} AND durable_writes = true;");
        }
    } 
    
	@Test
	void contextLoads() {
	}

}
