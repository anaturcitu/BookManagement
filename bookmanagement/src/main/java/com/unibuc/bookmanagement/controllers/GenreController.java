package com.unibuc.bookmanagement.controllers;

import com.unibuc.bookmanagement.models.Genre;
import com.unibuc.bookmanagement.services.GenreService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/genres")
public class GenreController {
    @Autowired
    private GenreService genreService;

    @GetMapping
    public List<Genre> getAllGenres() {
        List<Genre> genres = genreService.getAllGenres();
        log.info("S-au returnat {} genuri.", genres.size());
        return genreService.getAllGenres();
    }

    @PostMapping
    public Genre saveGenre(@Valid @RequestBody Genre genre) {
        Genre savedGenre = genreService.saveGenres(genre);
        log.info("Gen salvat: {}", savedGenre.getName());
        return genreService.saveGenres(genre);
    }

    @GetMapping("/{id}")
    public Genre getGenreById(@PathVariable Long id) {
        log.info("Cerere: obținere gen cu ID {}", id);
        Genre genre = genreService.getGenreById(id);
        if (genre != null) {
            log.info("Gen găsit: {}", genre.getName());
        } else {
            log.warn("Genul cu ID {} nu a fost găsit.", id);
        }
        return genre;
    }
}
