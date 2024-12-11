package com.unibuc.bookmanagement.unit_tests.endpoints;
import com.unibuc.bookmanagement.controllers.GenreController;
import com.unibuc.bookmanagement.models.Genre;
import com.unibuc.bookmanagement.services.GenreService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;


@WebMvcTest(GenreController.class)
public class GenreControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GenreService genreService;

    @Test
    void testGetAllGenres() throws Exception {
        Genre genre1 = new Genre();
        genre1.setId(1L);
        genre1.setName("Fiction");

        Genre genre2 = new Genre();
        genre2.setId(2L);
        genre2.setName("Non-fiction");

        List<Genre> genres = List.of(genre1, genre2);

        when(genreService.getAllGenres()).thenReturn(genres);

        mockMvc.perform(get("/api/genres"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value("Fiction"))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].name").value("Non-fiction"));

        verify(genreService, times(1)).getAllGenres();
    }

    @Test
    void testSaveGenre() throws Exception {
        Genre genre = new Genre();
        genre.setId(1L);
        genre.setName("Fiction");

        when(genreService.saveGenres(any(Genre.class))).thenReturn(genre);

        mockMvc.perform(post("/api/genres")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Fiction\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Fiction"));

        verify(genreService, times(1)).saveGenres(any(Genre.class));
    }

    @Test
    void testGetGenreById() throws Exception {
        Genre genre = new Genre();
        genre.setId(1L);
        genre.setName("Fiction");

        when(genreService.getGenreById(1L)).thenReturn(genre);

        mockMvc.perform(get("/api/genres/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Fiction"));

        verify(genreService, times(1)).getGenreById(1L);
    }

    @Test
    void testDeleteGenre() throws Exception {
        doNothing().when(genreService).deleteGenre(1L);

        mockMvc.perform(delete("/api/genres/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Genre deleted successfully"));

        verify(genreService, times(1)).deleteGenre(1L);
    }
}
