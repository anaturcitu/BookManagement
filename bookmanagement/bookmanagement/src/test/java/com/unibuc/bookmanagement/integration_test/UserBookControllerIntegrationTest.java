package com.unibuc.bookmanagement.integration_test;

import com.unibuc.bookmanagement.config.TestSecurityConfig;
import com.unibuc.bookmanagement.junction_tables.UserBookId;
import com.unibuc.bookmanagement.models.Book;
import com.unibuc.bookmanagement.models.User;
import com.unibuc.bookmanagement.models.UserBook;
import com.unibuc.bookmanagement.repositories.BookRepository;
import com.unibuc.bookmanagement.repositories.UserBookRepository;
import com.unibuc.bookmanagement.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Import({TestSecurityConfig.class})
@TestPropertySource(properties = "spring.main.allow-bean-definition-overriding=true")

public class UserBookControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate rest;

    @Autowired
    private UserBookRepository userBookRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;

    private String url(String path) {
        return "http://localhost:" + port + path;
    }

    @BeforeEach
    void clean() {
        // curata bazele de date inainte de fiecare test
        userBookRepository.deleteAll();
        userRepository.deleteAll();
        bookRepository.deleteAll();
    }

    @Test
    void whenAddUserBook_thenItIsPersisted() {
        // creeaza si salveaza un user
        User user = new User();
        user.setUsername("user1");
        user.setPassword("pass");
        user.setEmail("user1@example.com");
        user = userRepository.save(user);

        // creeaza si salveaza o carte
        Book book = new Book();
        book.setTitle("Carte Test");
        book.setAuthorId(1L);
        book.setIsbn("1234567890123");
        book = bookRepository.save(book);

        // construieste corpul json pentru post
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> body = new HashMap<>();
        body.put("user", Map.of("id", user.getId()));
        body.put("book", Map.of("id", book.getId()));

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

        // trimite cererea post
        ResponseEntity<String> response = rest.postForEntity(
                url("/api/userbooks"), request, String.class
        );

        // verifica daca am primit 200 si ca relatia e salvata
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(userBookRepository.findAll()).hasSize(1);
    }

    @Test
    void whenGetUserBooks_thenReturnsCorrectList() {
        // creeaza si salveaza user si carte
        User user = new User();
        user.setUsername("user2");
        user.setPassword("pass");
        user.setEmail("user2@example.com");
        user = userRepository.save(user);

        Book book = new Book();
        book.setTitle("Book 2");
        book.setAuthorId(2L);
        book.setIsbn("9876543210987");
        book = bookRepository.save(book);

        // creeaza obiectul userbook si seteaza relatia
        UserBook userBook = new UserBook();
        userBook.setUser(user);
        userBook.setBook(book);
        userBook.setId(new UserBookId(user.getId(), book.getId()));
        userBookRepository.save(userBook);

        // trimite cerere get catre endpoint
        ResponseEntity<UserBook[]> response = rest.getForEntity(
                url("/api/userbooks/users/" + user.getId()), UserBook[].class
        );

        // verifica statusul si ca titlul e cel corect
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotEmpty();
        assertThat(response.getBody()[0].getBook().getTitle()).isEqualTo("Book 2");
    }

    @Test
    void whenGetBookUsers_thenReturnsCorrectList() {
        // creeaza si salveaza user si carte
        User user = new User();
        user.setUsername("user3");
        user.setPassword("pass");
        user.setEmail("user3@example.com");
        user = userRepository.save(user);

        Book book = new Book();
        book.setTitle("Book 3");
        book.setAuthorId(3L);
        book.setIsbn("1112223334445");
        book = bookRepository.save(book);

        // creeaza obiectul userbook si seteaza relatia
        UserBook userBook = new UserBook();
        userBook.setUser(user);
        userBook.setBook(book);
        userBook.setId(new UserBookId(user.getId(), book.getId()));
        userBookRepository.save(userBook);

        // trimite cerere get catre endpoint
        ResponseEntity<UserBook[]> response = rest.getForEntity(
                url("/api/userbooks/books/" + book.getId()), UserBook[].class
        );

        // verifica statusul si ca userul asociat are username-ul asteptat
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotEmpty();
        assertThat(response.getBody()[0].getUser().getUsername()).isEqualTo("user3");
    }
}
