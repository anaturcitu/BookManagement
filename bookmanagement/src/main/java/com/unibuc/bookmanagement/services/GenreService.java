package com.unibuc.bookmanagement.services;

import com.unibuc.bookmanagement.models.Genre;
import com.unibuc.bookmanagement.repositories.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@Service
public class GenreService {

    @Autowired
    private GenreRepository genreRepository;

    public List<Genre> getAllGenres() {
        log.info("Se obține lista tuturor genurilor.");
        return genreRepository.findAll();
    }

    public Genre saveGenres(Genre genre) {
        log.info("Se salvează genul: {}", genre.getName());
        return genreRepository.save(genre);
    }

    public Genre getGenreById(Long id) {
        log.info("Se caută genul cu ID: {}", id);
        return genreRepository.findById(id).orElse(null);
    }

    // public void deleteGenre(Long id) {
    //     log.info("Se șterge genul cu ID: {}", id);
    //     genreRepository.deleteById(id);
    // }
}
