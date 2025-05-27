package com.unibuc.bookmanagement.integration_test;

import com.unibuc.bookmanagement.models.Book;
import com.unibuc.bookmanagement.models.Review;
import com.unibuc.bookmanagement.models.User;
import com.unibuc.bookmanagement.repositories.BookRepository;
import com.unibuc.bookmanagement.repositories.ReviewRepository;
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

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Import({TestSecurityConfig.class, NoSecurityConfig.class})
public class ReviewControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate rest;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;

    // construieste url-ul complet pentru request
    private String url(String path) {
        return "http://localhost:" + port + path;
    }

    // curata toate tabelele inainte de fiecare test
    @BeforeEach
    void cleanUp() {
        reviewRepository.deleteAll();
        bookRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void whenGetAllReviews_thenReturnsListCorrectly() {
        // salveaza un user fictiv
        User user = new User();
        user.setUsername("user1");
        user.setPassword("pass");
        user.setEmail("user1@example.com");
        User savedUser = userRepository.save(user);

        // salveaza o carte fictiva
        Book book = new Book();
        book.setTitle("Carte Test");
        book.setAuthorId(1L);
        book.setIsbn("1234567890123");
        Book savedBook = bookRepository.save(book);

        // salveaza o recenzie
        Review review = new Review();
        review.setContent("Foarte bun");
        review.setRating(5);
        review.setBook(savedBook);
        review.setUser(savedUser);
        reviewRepository.save(review);

        // trimite GET catre /reviews
        ResponseEntity<Review[]> response = rest.getForEntity(url("/reviews"), Review[].class);

        // verifica ca primim 200 OK si recenzia salvata
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().length).isGreaterThanOrEqualTo(1);
        assertThat(response.getBody()[0].getContent()).isEqualTo("Foarte bun");
    }
}
