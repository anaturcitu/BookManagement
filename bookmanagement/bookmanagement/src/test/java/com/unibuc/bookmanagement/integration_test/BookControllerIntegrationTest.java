package com.unibuc.bookmanagement.integration_test;

import com.unibuc.bookmanagement.models.Book;
import com.unibuc.bookmanagement.repositories.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;

import java.net.URI;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class BookControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private BookRepository bookRepository;

    private String getBaseUrl() {
        return "http://localhost:" + port + "/books";
    }

    @BeforeEach
    void setUp() {
        bookRepository.deleteAll();
    }

    @Test
    void testCreateAndGetBooks() {
        // creeaza carte
        Book book = new Book();
        book.setTitle("Integration Test Book");
        book.setDescription("Some description");
        book.setIsbn("1234567890123");
        book.setAuthorId(1L);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Book> request = new HttpEntity<>(book, headers);

        ResponseEntity<Book> postResponse = restTemplate.postForEntity(getBaseUrl(), request, Book.class);
        assertThat(postResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        // preia cartile
        ResponseEntity<Book[]> response = restTemplate.getForEntity(getBaseUrl(), Book[].class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotEmpty();
        assertThat(response.getBody()[0].getTitle()).isEqualTo("Integration Test Book");
    }
}
