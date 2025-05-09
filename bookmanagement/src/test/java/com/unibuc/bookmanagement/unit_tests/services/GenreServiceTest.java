package com.unibuc.bookmanagement.unit_tests.services;

import com.unibuc.bookmanagement.models.Genre;
import com.unibuc.bookmanagement.repositories.GenreRepository;
import com.unibuc.bookmanagement.services.GenreService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GenreServiceTest {
    @Mock
    private GenreRepository genreRepository;

    @InjectMocks
    private GenreService genreService;

    @Test
    void testGetAllGenres() {
        List<Genre> genres = List.of(new Genre(), new Genre());
        when(genreRepository.findAll()).thenReturn(genres);

        List<Genre> result = genreService.getAllGenres();

        assertEquals(2, result.size());
        verify(genreRepository, times(1)).findAll();
    }

    @Test
    void testSaveGenres() {
        Genre genre = new Genre();
        genre.setName("Fiction");

        when(genreRepository.save(any(Genre.class))).thenReturn(genre);

        Genre result = genreService.saveGenres(genre);

        assertNotNull(result);
        assertEquals("Fiction", result.getName());
        verify(genreRepository, times(1)).save(genre);
    }

    @Test
    void testGetGenreById() {
        Genre genre = new Genre();
        genre.setId(1L);
        genre.setName("Mystery");

        when(genreRepository.findById(1L)).thenReturn(Optional.of(genre));

        Genre result = genreService.getGenreById(1L);

        assertNotNull(result);
        assertEquals("Mystery", result.getName());
        verify(genreRepository, times(1)).findById(1L);
    }
}
