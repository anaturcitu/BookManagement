// package com.unibuc.bookmanagement.integration_test;

// import com.unibuc.bookmanagement.models.Author;
// import com.unibuc.bookmanagement.models.Book;
// import com.unibuc.bookmanagement.repositories.AuthorRepository;
// import com.unibuc.bookmanagement.repositories.BookRepository;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.boot.test.context.TestConfiguration;
// import org.springframework.boot.test.web.client.TestRestTemplate;
// import org.springframework.boot.test.web.server.LocalServerPort;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Import;
// import org.springframework.context.annotation.Primary;
// import org.springframework.context.annotation.Profile;
// import org.springframework.http.HttpEntity;
// import org.springframework.http.HttpHeaders;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.MediaType;
// import org.springframework.http.ResponseEntity;
// import org.springframework.security.authentication.AuthenticationManager;
// import org.springframework.security.config.Customizer;
// import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
// import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.core.userdetails.UserDetails;
// import org.springframework.security.core.userdetails.UserDetailsService;
// import org.springframework.security.crypto.password.NoOpPasswordEncoder;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.security.provisioning.InMemoryUserDetailsManager;
// import org.springframework.security.web.SecurityFilterChain;
// import org.springframework.test.context.ActiveProfiles;
// import org.springframework.util.LinkedMultiValueMap;
// import org.springframework.util.MultiValueMap;

// import static org.assertj.core.api.Assertions.assertThat;

// @SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
// @ActiveProfiles("test")
// @Import(BookControllerIntegrationTest.TestSecurityConfig.class)
// @TestPropertySource(properties = "spring.main.allow-bean-definition-overriding=true")
// class BookControllerIntegrationTest {

//     @LocalServerPort
//     int port;

//     @Autowired
//     TestRestTemplate restTemplate;

//     @Autowired
//     BookRepository bookRepository;

//     @Autowired
//     AuthorRepository authorRepository;

//     private String base(String path) {
//         return "http://localhost:" + port + path;
//     }

//     @BeforeEach
//     void clean() {
//         // golește atât books cât și authors înainte de fiecare test
//         bookRepository.deleteAll();
//         authorRepository.deleteAll();
//     }

//     @Test
//     void createAndFetchBook() {
//         // 1) Creează un autor valid direct cu setter-ele din model
//         Author author = new Author();
//         author.setName("Autor de Test");
//         author.setBirth_date("1980-01-01");    // setterul e setBirth_date(String)
//         Author saved = authorRepository.save(author);

//         // 2) Pregătește formularul folosind ID-ul autorului creat mai sus
//         MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
//         form.add("title",       "Integration Test Book");
//         form.add("isbn",        "1234567890123");
//         form.add("description", "Some description");
//         form.add("authorId",    String.valueOf(saved.getId()));

//         HttpHeaders headers = new HttpHeaders();
//         headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

//         // 3) Trimite POST cu Basic Auth pentru "admin"
//         TestRestTemplate admin = restTemplate.withBasicAuth("admin", "admin123");
//         ResponseEntity<Void> post = admin.postForEntity(
//             base("/books/add"),
//             new HttpEntity<>(form, headers),
//             Void.class
//         );

//         // 4) Acum va răspunde cu 302 FOUND, iar cartea va fi salvată
//         assertThat(post.getStatusCode()).isEqualTo(HttpStatus.FOUND);
//         assertThat(bookRepository.count()).isEqualTo(1);
//         assertThat(bookRepository.findAll().get(0).getTitle())
//             .isEqualTo("Integration Test Book");
//     }

//     @TestConfiguration
//     @Profile("test")
//     static class TestSecurityConfig {

//         @Bean
//         public PasswordEncoder passwordEncoder() {
//             return NoOpPasswordEncoder.getInstance();
//         }

//         @Bean
//         @Primary
//         public UserDetailsService userDetailsService() {
//             UserDetails admin = org.springframework.security.core.userdetails.User
//                     .withUsername("admin")
//                     .password("admin123")
//                     .roles("ADMIN")
//                     .build();
//             return new InMemoryUserDetailsManager(admin);
//         }

//         @Bean
//         public AuthenticationManager authenticationManager(HttpSecurity http,
//                                                            PasswordEncoder passwordEncoder,
//                                                            UserDetailsService uds) throws Exception {
//             return http.getSharedObject(AuthenticationManagerBuilder.class)
//                        .userDetailsService(uds)
//                        .passwordEncoder(passwordEncoder)
//                        .and()
//                        .build();
//         }

//         @Bean
//         public SecurityFilterChain filterChain(HttpSecurity http,
//                                                AuthenticationManager authManager) throws Exception {
//             http
//                 .authenticationManager(authManager)
//                 .csrf(csrf -> csrf.disable())
//                 .authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
//                 .httpBasic(Customizer.withDefaults());
//             return http.build();
//         }
//     }
// }
