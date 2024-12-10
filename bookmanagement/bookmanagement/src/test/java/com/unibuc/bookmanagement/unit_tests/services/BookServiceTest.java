package com.unibuc.bookmanagement.unit_tests.services;

import com.unibuc.bookmanagement.models.Book;
import com.unibuc.bookmanagement.repositories.BookRepository;
import com.unibuc.bookmanagement.services.BookService;
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
public class BookServiceTest {
    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    @Test
    void testCreateBook() {
        Book book = new Book();
        book.setId(1L);
        book.setTitle("Test Book");
        book.setAuthorId(10L);

        when(bookRepository.save(any(Book.class))).thenReturn(book);

        Book createdBook = bookService.createBook(book);

        assertNotNull(createdBook);
        assertEquals(1L, createdBook.getId());
        assertEquals("Test Book", createdBook.getTitle());
        assertEquals(10L, createdBook.getAuthorId());
        verify(bookRepository, times(1)).save(book);
    }

    @Test
    void testGetBookById() {
        Book book = new Book();
        book.setId(1L);
        book.setTitle("Test Book");
        book.setAuthorId(10L);

        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        Optional<Book> result = bookService.getBookById(1L);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
        assertEquals("Test Book", result.get().getTitle());
        verify(bookRepository, times(1)).findById(1L);
    }

    @Test
    void testGetAllBooks() {
        Book book1 = new Book();
        book1.setId(1L);
        book1.setTitle("Test Book 1");
        book1.setAuthorId(10L);

        Book book2 = new Book();
        book2.setId(2L);
        book2.setTitle("Test Book 2");
        book2.setAuthorId(10L);

        List<Book> books = Arrays.asList(book1, book2);

        when(bookRepository.findAll()).thenReturn(books);

        List<Book> result = bookService.getAllBooks();

        assertEquals(2, result.size());
        assertEquals("Test Book 1", result.get(0).getTitle());
        assertEquals("Test Book 2", result.get(1).getTitle());
        verify(bookRepository, times(1)).findAll();
    }

    @Test
    void testDeleteBook() {
        Long bookId = 1L;

        doNothing().when(bookRepository).deleteById(bookId);

        bookService.deleteBook(bookId);

        verify(bookRepository, times(1)).deleteById(bookId);
    }
}
