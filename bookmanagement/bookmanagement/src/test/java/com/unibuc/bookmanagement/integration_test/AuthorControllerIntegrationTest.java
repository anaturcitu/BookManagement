package com.unibuc.bookmanagement.integration_test;

import com.unibuc.bookmanagement.config.TestSecurityConfig;
import com.unibuc.bookmanagement.repositories.AuthorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.DefaultCsrfToken;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Import({TestSecurityConfig.class})
@TestPropertySource(properties = "spring.main.allow-bean-definition-overriding=true")
public class AuthorControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate rest;

    @Autowired
    private AuthorRepository authorRepository;

    private String url(String path) {
        return "http://localhost:" + port + path;
    }

    @BeforeEach
    void clearDb() {
        authorRepository.deleteAll();
    }

    @ControllerAdvice
    @Profile("test")
    public static class CsrfControllerAdvice {

        @ModelAttribute("_csrf")
        public CsrfToken csrfToken() {
            return new DefaultCsrfToken("X-CSRF-TOKEN", "_csrf", "dummy");
        }
    }

    @Test
    void testShowAddAuthorForm() {
        ResponseEntity<String> response = rest.getForEntity(url("/authors/add"), String.class);

        System.out.println("=== RESPONSE BODY ===");
        System.out.println(response.getBody());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).contains("Adaugă un autor");
    }
}
