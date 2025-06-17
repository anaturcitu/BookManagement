package com.example.genre_service.service;

import com.example.genre_service.model.Genre;
import com.example.genre_service.repository.GenreRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenreService {

    private static final Logger logger = LoggerFactory.getLogger(GenreService.class);

    @Autowired
    private GenreRepository genreRepository;

    public List<Genre> getAllGenres() {
        logger.info("Service - Se obțin toate genurile din bază");
        return genreRepository.findAll();
    }

    public Genre saveGenres(Genre genre) {
        logger.info("Service - Se salvează un nou gen: {}", genre.getName());
        Genre saved = genreRepository.save(genre);
        logger.info("Gen salvat cu ID-ul: {}", saved.getId());
        return saved;
    }

    public Genre getGenreById(Long id) {
        logger.info("Service - Se caută genul cu ID-ul: {}", id);
        return genreRepository.findById(id).orElse(null);
    }

//    public void deleteGenre(Long id) {
//        genreRepository.deleteById(id);
//    }
}
