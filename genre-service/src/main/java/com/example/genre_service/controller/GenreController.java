package com.example.genre_service.controller;

import com.example.genre_service.model.Genre;
import com.example.genre_service.service.GenreService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/genres")
public class GenreController {
    private static final Logger logger = LoggerFactory.getLogger(GenreController.class);

    @Autowired
    private GenreService genreService;

    @GetMapping
    public List<Genre> getAllGenres() {
        logger.info("Solicitare pentru obținerea tuturor genurilor");
        List<Genre> genres = genreService.getAllGenres();
        logger.info("Returnare {} genuri", genres.size());
        return genres;
    }

    @PostMapping
    public Genre saveGenre(@Valid @RequestBody Genre genre) {
        logger.info("Solicitare pentru salvarea genului: {}", genre.getName());
        Genre savedGenre = genreService.saveGenres(genre);
        logger.info("Gen salvat cu ID-ul: {}", savedGenre.getId());
        return savedGenre;
    }

    @GetMapping("/{id}")
    public Genre getGenreById(@PathVariable Long id) {
        logger.info("Solicitare pentru obținerea genului cu ID-ul: {}", id);
        Genre genre = genreService.getGenreById(id);
        if (genre != null) {
            logger.info("Gen găsit: {}", genre.getName());
        } else {
            logger.warn("Genul cu ID-ul {} nu a fost găsit", id);
        }
        return genre;
    }
}
