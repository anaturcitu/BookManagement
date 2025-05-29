package com.unibuc.bookmanagement.integration_test;

import com.unibuc.bookmanagement.config.TestSecurityConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Import({TestSecurityConfig.class})
@TestPropertySource(properties = "spring.main.allow-bean-definition-overriding=true")
public class HomeControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String url(String path) {
        return "http://localhost:" + port + path;
    }

    @Test
    void whenAccessHome_thenIsRedirectedToBooks() {
        ResponseEntity<String> response = restTemplate.getForEntity(url("/"), String.class);

        // Verifică că statusul este 302 FOUND (redirect)
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FOUND);

        // Verifică că redirectul este către /books
        assertThat(response.getHeaders().getLocation().toString()).contains("/books");
    }
}
