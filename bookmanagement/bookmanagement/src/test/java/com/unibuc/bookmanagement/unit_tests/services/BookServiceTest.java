package com.unibuc.bookmanagement.unit_tests.services;

import com.unibuc.bookmanagement.dto.BookDTO;
import com.unibuc.bookmanagement.models.Book;
import com.unibuc.bookmanagement.repositories.BookRepository;
import com.unibuc.bookmanagement.services.BookService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    @Test
    void testCreateBook() {
        BookDTO bookDTO = new BookDTO();
        bookDTO.setTitle("Carte de Test");
        bookDTO.setIsbn("1234567890");
        bookDTO.setDescription("O carte de test");
        bookDTO.setAuthorId(1L);

        Book expectedBook = new Book();
        expectedBook.setTitle(bookDTO.getTitle());
        expectedBook.setIsbn(bookDTO.getIsbn());
        expectedBook.setDescription(bookDTO.getDescription());
        expectedBook.setAuthorId(bookDTO.getAuthorId());

        when(bookRepository.save(any(Book.class))).thenReturn(expectedBook);

        Book result = bookService.createBook(bookDTO);

        assertNotNull(result);
        assertEquals("Carte de Test", result.getTitle());
        assertEquals("1234567890", result.getIsbn());
        verify(bookRepository, times(1)).save(any(Book.class));
    }

    @Test
    void testGetBookById() {
        Book book = new Book();
        book.setId(1L);
        book.setTitle("Carte X");

        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        Optional<Book> result = bookService.getBookById(1L);

        assertTrue(result.isPresent());
        assertEquals("Carte X", result.get().getTitle());
        verify(bookRepository, times(1)).findById(1L);
    }

    @Test
    void testGetAllBooks() {
        Book b1 = new Book();
        Book b2 = new Book();

        when(bookRepository.findAll()).thenReturn(Arrays.asList(b1, b2));

        List<Book> result = bookService.getAllBooks();

        assertEquals(2, result.size());
        verify(bookRepository, times(1)).findAll();
    }

    @Test
    void testDeleteBook() {
        Long id = 5L;

        doNothing().when(bookRepository).deleteById(id);

        bookService.deleteBook(id);

        verify(bookRepository, times(1)).deleteById(id);
    }

    @Test
    void testGetBookByTitle() {
        Book book = new Book();
        book.setTitle("Test");

        when(bookRepository.findByTitle("Test")).thenReturn(Optional.of(book));

        Optional<Book> result = bookService.getBookByTitle("Test");

        assertTrue(result.isPresent());
        assertEquals("Test", result.get().getTitle());
        verify(bookRepository, times(1)).findByTitle("Test");
    }

    @Test
    void testGetBooksPaginated() {
        Book book1 = new Book();
        book1.setTitle("A");

        Book book2 = new Book();
        book2.setTitle("B");

        Pageable pageable = PageRequest.of(0, 2, Sort.by("title"));
        Page<Book> page = new PageImpl<>(List.of(book1, book2), pageable, 2);

        when(bookRepository.findAll(pageable)).thenReturn(page);

        Page<Book> result = bookService.getBooks(pageable);

        assertEquals(2, result.getTotalElements());
        assertEquals("A", result.getContent().get(0).getTitle());
        verify(bookRepository, times(1)).findAll(pageable);
    }
}
