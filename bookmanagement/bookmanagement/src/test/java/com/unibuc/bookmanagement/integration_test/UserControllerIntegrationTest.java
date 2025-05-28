// package com.unibuc.bookmanagement.integration_test;

// import static org.assertj.core.api.Assertions.assertThat;

// import java.util.regex.Matcher;
// import java.util.regex.Pattern;

// import com.unibuc.bookmanagement.BookmanagementApplication;
// import com.unibuc.bookmanagement.repositories.UserRepository;

// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.boot.test.context.TestConfiguration;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Primary;
// import org.springframework.test.context.ActiveProfiles;
// import org.springframework.boot.test.web.client.TestRestTemplate;
// import org.springframework.boot.test.web.server.LocalServerPort;
// import org.springframework.http.*;
// import org.springframework.util.LinkedMultiValueMap;
// import org.springframework.util.MultiValueMap;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// import org.springframework.security.crypto.password.PasswordEncoder;

// @SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
// @ActiveProfiles("test")

// public class UserControllerIntegrationTest {

//     @LocalServerPort
//     private int port;

//     @Autowired
//     private TestRestTemplate rest;

//     @Autowired
//     private UserRepository userRepository;

//     private String baseUrl;

//     @BeforeEach
//     void setUp() {
//         userRepository.deleteAll();
//         baseUrl = "http://localhost:" + port;
//     }

//     private String url(String uri) {
//         return baseUrl + uri;
//     }

//     /**
//      * Acestei clase interne statice îi spunem Spring-ului de test că definește un bean
//      * de tip PasswordEncoder care va satisface dependența din AdminInitializer.
//      */
//     @TestConfiguration
//     static class TestSecurityConfig {
//         @Bean
//         @Primary
//         public PasswordEncoder passwordEncoder() {
//             return new BCryptPasswordEncoder();
//         }
//     }

//     /**
//      * Extrage token-ul CSRF din corpul HTML al paginii de formular.
//      */
//     private String extractCsrfToken(String body) {
//         Pattern pattern = Pattern.compile("name=\"_csrf\".*?value=\"([^\"]+)\"");
//         Matcher matcher = pattern.matcher(body);
//         if (!matcher.find()) {
//             throw new IllegalStateException("CSRF token not found in response");
//         }
//         return matcher.group(1);
//     }

//     @Test
//     public void testSuccessfulRegistration() {
//         // 1) GET pe /users/register ca să luăm token-ul și cookie-ul de sesiune
//         ResponseEntity<String> getResponse = rest.getForEntity(url("/users/register"), String.class);
//         HttpHeaders headers = new HttpHeaders();
//         headers.set(HttpHeaders.COOKIE, getResponse.getHeaders().getFirst(HttpHeaders.SET_COOKIE));
//         String csrfToken = extractCsrfToken(getResponse.getBody());

//         // 2) Construim formularul cu parametrii și token-ul CSRF
//         MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
//         form.add("username", "testuser");
//         form.add("password", "pass123");
//         form.add("confirmPassword", "pass123");
//         form.add("_csrf", csrfToken);

//         HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(form, headers);

//         // 3) POST și verificăm redirect-ul
//         ResponseEntity<String> response = rest.postForEntity(url("/users/register"), request, String.class);
//         assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FOUND);
//         assertThat(response.getHeaders().getLocation().getPath()).isEqualTo("/users/login");
//     }

//     @Test
//     public void testRegistrationPasswordMismatch() {
//         // aceeași schemă: GET + extract CSRF
//         ResponseEntity<String> getResponse = rest.getForEntity(url("/users/register"), String.class);
//         HttpHeaders headers = new HttpHeaders();
//         headers.set(HttpHeaders.COOKIE, getResponse.getHeaders().getFirst(HttpHeaders.SET_COOKIE));
//         String csrfToken = extractCsrfToken(getResponse.getBody());

//         // trimitem parole diferite
//         MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
//         form.add("username", "testuser");
//         form.add("password", "pass123");
//         form.add("confirmPassword", "different");
//         form.add("_csrf", csrfToken);

//         HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(form, headers);
//         ResponseEntity<String> response = rest.postForEntity(url("/users/register"), request, String.class);

//         // rămâne pe pagină și apare mesajul de eroare
//         assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
//         assertThat(response.getBody()).contains("Parolele nu se potrivesc");
//     }
// }
