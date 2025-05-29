package com.unibuc.bookmanagement.integration_test;

import com.unibuc.bookmanagement.config.TestSecurityConfig;
import com.unibuc.bookmanagement.models.Genre;
import com.unibuc.bookmanagement.repositories.GenreRepository;
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

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Import({TestSecurityConfig.class})
@TestPropertySource(properties = "spring.main.allow-bean-definition-overriding=true")

public class GenreControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate rest;

    @Autowired
    private GenreRepository genreRepository;

    // construieste url-ul complet pentru request
    private String url(String path) {
        return "http://localhost:" + port + path;
    }

    // curata tabela inainte de fiecare test
    @BeforeEach
    void cleanUp() {
        genreRepository.deleteAll();
    }

    @Test
    void whenCreateGenre_thenItIsPersisted() {
        // creeaza un obiect genre
        Genre genre = new Genre();
        genre.setName("Fantasy");

        // trimite un POST catre /api/genres
        ResponseEntity<Genre> response = rest.postForEntity(url("/api/genres"), genre, Genre.class);

        // verifica ca genul a fost creat si salvat
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getName()).isEqualTo("Fantasy");

        List<Genre> all = genreRepository.findAll();
        assertThat(all).hasSize(1);
    }

    @Test
    void whenGetAllGenres_thenReturnsCorrectList() {
        // adauga un gen in baza de date
        Genre genre = new Genre();
        genre.setName("Sci-Fi");
        genreRepository.save(genre);

        // trimite un GET catre /api/genres
        ResponseEntity<Genre[]> response = rest.getForEntity(url("/api/genres"), Genre[].class);

        // verifica ca lista nu e goala
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotEmpty();
        assertThat(response.getBody()[0].getName()).isEqualTo("Sci-Fi");
    }

    @Test
    void whenGetGenreById_thenReturnsCorrectGenre() {
        // salveaza un gen
        Genre genre = new Genre();
        genre.setName("Horror");
        Genre saved = genreRepository.save(genre);

        // trimite un GET catre /api/genres/{id}
        ResponseEntity<Genre> response = rest.getForEntity(url("/api/genres/" + saved.getId()), Genre.class);

        // verifica ca raspunsul e corect
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getName()).isEqualTo("Horror");
    }

    @Test
    void whenGetGenreByInvalidId_thenReturnsNullBody() {
        // GET pe un ID inexistent
        ResponseEntity<Genre> response = rest.getForEntity(url("/api/genres/9999"), Genre.class);

        // raspunsul HTTP e 200 dar body-ul trebuie sa fie null
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNull();
    }
}
