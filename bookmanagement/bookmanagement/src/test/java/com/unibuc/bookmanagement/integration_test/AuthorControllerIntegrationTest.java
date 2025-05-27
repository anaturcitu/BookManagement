package com.unibuc.bookmanagement.integration_test;

import com.unibuc.bookmanagement.models.Author;
import com.unibuc.bookmanagement.repositories.AuthorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Import({TestSecurityConfig.class, NoSecurityConfig.class})
public class AuthorControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate rest;

    @Autowired
    private AuthorRepository authorRepository;

    // construieste url-ul complet pentru request
    private String url(String path) {
        return "http://localhost:" + port + path;
    }

    // curata tabela inainte de fiecare test
    @BeforeEach
    void cleanUp() {
        authorRepository.deleteAll();
    }

    @Test
    void whenCreateAuthor_thenAuthorIsPersisted() {
        // creeaza un obiect author de test
        Author author = new Author();
        author.setName("Test Author");

        // trimite un POST catre /api/authors
        ResponseEntity<Author> response = rest.postForEntity(url("/api/authors"), author, Author.class);

        // verifica ca raspunsul este 200 si autorul a fost salvat
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getName()).isEqualTo("Test Author");

        List<Author> authors = authorRepository.findAll();
        assertThat(authors).hasSize(1);
    }

    @Test
    void whenGetAllAuthors_thenReturnsEmptyListInitially() {
        // trimite un GET catre /api/authors
        ResponseEntity<Author[]> response = rest.getForEntity(url("/api/authors"), Author[].class);

        // verifica ca lista e goala initial
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEmpty();
    }

    @Test
    void whenGetAuthorById_thenReturnsCorrectAuthor() {
        // salveaza un autor direct in baza pentru test
        Author saved = authorRepository.save(new Author(null, "Existing Author", "1990-01-01"));

        // trimite un GET catre /api/authors/{id}
        ResponseEntity<Author> response = rest.getForEntity(url("/api/authors/" + saved.getId()), Author.class);

        // verifica ca autorul returnat e cel corect
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getName()).isEqualTo("Existing Author");
    }

    @Test
    void whenGetAuthorByInvalidId_thenReturnsNotFound() {
        // trimite un GET cu un id care nu exista
        ResponseEntity<Author> response = rest.getForEntity(url("/api/authors/9999"), Author.class);

        // verifica ca raspunsul este 404
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void whenDeleteAuthor_thenAuthorIsRemoved() {
        // salveaza un autor pentru test
        Author saved = authorRepository.save(new Author(null, "Existing Author", "1990-01-01"));

        // trimite un DELETE catre /api/authors/{id}
        rest.delete(url("/api/authors/" + saved.getId()));

        // verifica ca autorul a fost sters
        assertThat(authorRepository.findById(saved.getId())).isEmpty();
    }
}
