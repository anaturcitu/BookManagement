package com.unibuc.bookmanagement.unit_tests.endpoints;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.unibuc.bookmanagement.controllers.GenreController;
import com.unibuc.bookmanagement.models.Genre;
import com.unibuc.bookmanagement.services.GenreService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(GenreController.class)
@AutoConfigureMockMvc(addFilters = false)
public class GenreControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GenreService genreService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetAllGenres() throws Exception {
        Genre genre1 = new Genre();
        genre1.setId(1L);
        genre1.setName("Ficțiune");

        Genre genre2 = new Genre();
        genre2.setId(2L);
        genre2.setName("Fantasy");

        when(genreService.getAllGenres()).thenReturn(List.of(genre1, genre2));

        mockMvc.perform(get("/api/genres"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("Ficțiune"))
                .andExpect(jsonPath("$[1].name").value("Fantasy"));
    }

    @Test
    void testGetGenreById_found() throws Exception {
        Genre genre = new Genre();
        genre.setId(1L);
        genre.setName("Istorie");

        when(genreService.getGenreById(1L)).thenReturn(genre);

        mockMvc.perform(get("/api/genres/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Istorie"));
    }

    @Test
    void testGetGenreById_notFound() throws Exception {
        when(genreService.getGenreById(99L)).thenReturn(null);

        mockMvc.perform(get("/api/genres/99"))
                .andExpect(status().isOk())
                .andExpect(content().string("")); // răspuns gol
    }

    @Test
    void testSaveGenre() throws Exception {
        Genre genre = new Genre();
        genre.setId(1L);
        genre.setName("Psihologie");

        when(genreService.saveGenres(any(Genre.class))).thenReturn(genre);

        mockMvc.perform(post("/api/genres")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(genre)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Psihologie"));
    }
}
