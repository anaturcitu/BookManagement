package com.unibuc.bookmanagement.unit_tests.services;

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
        Author author = new Author();
        author.setName("Andrei Popescu");

        when(authorRepository.save(any(Author.class))).thenReturn(author);

        Author result = authorService.createAuthor(author);

        assertNotNull(result);
        assertEquals("Andrei Popescu", result.getName());
        verify(authorRepository, times(1)).save(author);
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
