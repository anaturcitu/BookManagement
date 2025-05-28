package com.unibuc.bookmanagement.unit_tests.services;

import com.unibuc.bookmanagement.dto.AuthorDTO;
import com.unibuc.bookmanagement.models.Author;
import com.unibuc.bookmanagement.repositories.AuthorRepository;
import com.unibuc.bookmanagement.services.AuthorService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthorServiceTest {
    @InjectMocks
    private AuthorService authorService;

    @Mock
    private AuthorRepository authorRepository;

    @Test
    void testCreateAuthor() {
        // Creez un AuthorDTO
        AuthorDTO dto = new AuthorDTO();
        dto.setName("Andrei Popescu");
        dto.setBirthDate("2000-01-01"); // string valid pt LocalDate.parse()

        // Creez obiectul Author care va fi returnat din mock
        Author savedAuthor = new Author();
        savedAuthor.setName("Andrei Popescu");

        when(authorRepository.save(any(Author.class))).thenReturn(savedAuthor);

        // Apelul testat
        Author result = authorService.createAuthor(dto);

        // Verificări
        assertNotNull(result);
        assertEquals("Andrei Popescu", result.getName());
        verify(authorRepository, times(1)).save(any(Author.class));
    }

    @Test
    void testGetAuthorByName() {
        Author author = new Author();
        author.setName("Maria Ionescu");

        when(authorRepository.findByName("Maria Ionescu")).thenReturn(Optional.of(author));

        Optional<Author> result = authorService.getAuthorByName("Maria Ionescu");

        assertTrue(result.isPresent());
        assertEquals("Maria Ionescu", result.get().getName());
        verify(authorRepository, times(1)).findByName("Maria Ionescu");
    }

    @Test
    void testGetAuthorById() {
        Author author = new Author();
        author.setId(1L);
        author.setName("Andrei Popescu");

        when(authorRepository.findById(1L)).thenReturn(Optional.of(author));

        Optional<Author> result = authorService.getAuthorById(1L);

        assertTrue(result.isPresent());
        assertEquals("Andrei Popescu", result.get().getName());
        verify(authorRepository, times(1)).findById(1L);
    }

    @Test
    void testGetAllAuthors() {
        List<Author> authors = Arrays.asList(new Author(), new Author());

        when(authorRepository.findAll()).thenReturn(authors);

        List<Author> result = authorService.getAllAuthors();

        assertEquals(2, result.size());
        verify(authorRepository, times(1)).findAll();
    }

    @Test
    void testDeleteAuthor() {
        Long authorId = 1L;

        doNothing().when(authorRepository).deleteById(authorId);

        authorService.deleteAuthor(authorId);

        verify(authorRepository, times(1)).deleteById(authorId);
    }
}
