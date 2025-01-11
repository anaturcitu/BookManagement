package com.unibuc.bookmanagement.services;

import com.unibuc.bookmanagement.models.Genre;
import com.unibuc.bookmanagement.repositories.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenreService {
    @Autowired
    private GenreRepository genreRepository;

    public List<Genre> getAllGenres() {
        return genreRepository.findAll();
    }

    public Genre saveGenres(Genre genre) {
        return genreRepository.save(genre);
    }

    public Genre getGenreById(Long id) {
        return genreRepository.findById(id).orElse(null);
    }

//    public void deleteGenre(Long id) {
//        genreRepository.deleteById(id);
//    }
}
