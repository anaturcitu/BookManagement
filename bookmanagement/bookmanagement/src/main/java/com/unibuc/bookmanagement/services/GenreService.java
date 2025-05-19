package com.unibuc.bookmanagement.services;

import com.unibuc.bookmanagement.models.Genre;
import com.unibuc.bookmanagement.repositories.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Service
public class GenreService {

    private static final Logger logger = LoggerFactory.getLogger(GenreService.class);

    @Autowired
    private GenreRepository genreRepository;

    public List<Genre> getAllGenres() {
        logger.info("Se obțin toate genurile din bază");
        return genreRepository.findAll();
    }

    public Genre saveGenres(Genre genre) {
        logger.info("Se salvează un nou gen: {}", genre.getName());
        return genreRepository.save(genre);
    }

    public Genre getGenreById(Long id) {
        logger.info("Se caută genul cu ID-ul: {}", id);
        return genreRepository.findById(id).orElse(null);
    }

//    public void deleteGenre(Long id) {
//        genreRepository.deleteById(id);
//    }
}
