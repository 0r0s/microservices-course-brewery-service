package com.novilabs.integration;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = {"spring.profiles.active=test"})
public class AbstractIntegrationTest {
    protected final TestRestTemplate testRestTemplate = new TestRestTemplate();

    @LocalServerPort
    private String port;

    protected String absUrl(String path) {
        return "http://localhost:" + port + path;
    }
}
