package com.unibuc.bookmanagement.integration_test;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test") // activeaza profilul "test"
@Import({TestSecurityConfig.class, NoSecurityConfig.class})
public class HomeControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    // construieste url-ul complet pentru request
    private String url(String path) {
        return "http://localhost:" + port + path;
    }

    @Test
    void whenAccessHome_thenReturnsBooksPage() {
        // trimite un GET catre "/"
        ResponseEntity<String> response = restTemplate.getForEntity(url("/"), String.class);

        // verifica ca raspunsul are status 200 OK
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        // verifica ca raspunsul contine continutul specific paginii de carti
        assertThat(response.getBody()).contains("Lista cări");
    }
}
