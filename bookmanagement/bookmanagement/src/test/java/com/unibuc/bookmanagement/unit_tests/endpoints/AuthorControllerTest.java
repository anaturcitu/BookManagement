package com.unibuc.bookmanagement.unit_tests.endpoints;

import com.unibuc.bookmanagement.controllers.AuthorController;
import com.unibuc.bookmanagement.models.Author;
import com.unibuc.bookmanagement.services.AuthorService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import java.awt.*;
import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

import org.springframework.http.MediaType;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


//@Import(JacksonAutoConfiguration.class)
@WebMvcTest(AuthorController.class)
public class AuthorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthorService authorService;

    @Test
    void testCreateAuthor() throws Exception {
        Author author = new Author();
        author.setId(1L);
        author.setName("Andrei Popescu");

        when(authorService.createAuthor(any(Author.class))).thenReturn(author);

        mockMvc.perform(post("/api/authors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Andrei Popescu\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Andrei Popescu"));
    }

    @Test
    void testGetAuthorById() throws Exception {
        Author author = new Author();
        author.setId(1L);
        author.setName("Andrei Popescu");

        when(authorService.getAuthorById(1L)).thenReturn(Optional.of(author));

        mockMvc.perform(get("/api/authors/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Andrei Popescu"));

        verify(authorService, times(1)).getAuthorById(1L);
    }

    @Test
    void testGetAllAuthors() throws Exception {
        Author author1 = new Author();
        author1.setId(1L);
        author1.setName("Andrei Popescu");

        Author author2 = new Author();
        author2.setId(2L);
        author2.setName("Andreea Popescu");

        when(authorService.getAllAuthors()).thenReturn(Arrays.asList(author1, author2));

        mockMvc.perform(get("/api/authors"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value("Andrei Popescu"))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].name").value("Andreea Popescu"));

        verify(authorService, times(1)).getAllAuthors();
    }

    @Test
    void testDeleteAuthor() throws Exception {
        doNothing().when(authorService).deleteAuthor(1L);

        mockMvc.perform(delete("/api/authors/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Author deleted successfully"));

        verify(authorService, times(1)).deleteAuthor(1L);
    }
}
