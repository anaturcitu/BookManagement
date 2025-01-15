package com.unibuc.bookmanagement.unit_tests.endpoints;
import com.unibuc.bookmanagement.controllers.BookController;
import com.unibuc.bookmanagement.models.Book;
import com.unibuc.bookmanagement.services.BookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.http.MediaType;
import java.util.Optional;

import java.util.List;
import java.util.Arrays;


@WebMvcTest(BookController.class)
public class BookControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @Test
    void testCreateBook() throws Exception {
        Book book = new Book();
        book.setId(1L);
        book.setTitle("Test Book");
        book.setAuthorId(10L);
        book.setIsbn("1234567890");

        when(bookService.createBook(any(Book.class))).thenReturn(book);

        mockMvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"Test Book\",\"author\":\"Test Author\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value("Test Book"))
                .andExpect(jsonPath("$.authorId").value(10L))
                .andExpect(jsonPath("$.isbn").value("1234567890"));

        verify(bookService, times(1)).createBook(any(Book.class));
    }

    @Test
    void testGetBookById() throws Exception {
        Book book = new Book();
        book.setId(1L);
        book.setTitle("Test Book");
        book.setAuthorId(10L);

        when(bookService.getBookById(1L)).thenReturn(Optional.of(book));

        mockMvc.perform(get("/api/books/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value("Test Book"))
                .andExpect(jsonPath("$.authorId").value(10L));

        verify(bookService, times(1)).getBookById(1L);
    }

    @Test
    void testGetBookById_NotFound() throws Exception {
        when(bookService.getBookById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/books/1"))
                .andExpect(status().isNotFound());

        verify(bookService, times(1)).getBookById(1L);
    }

    @Test
    void testGetAllBooks() throws Exception {
        Book book1 = new Book();
        book1.setId(1L);
        book1.setTitle("Test Book 1");
        book1.setAuthorId(10L);

        Book book2 = new Book();
        book2.setId(2L);
        book2.setTitle("Test Book 2");
        book2.setAuthorId(20L);

        List<Book> books = Arrays.asList(book1, book2);

        when(bookService.getAllBooks()).thenReturn(books);

        mockMvc.perform(get("/api/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].title").value("Test Book 1"))
                .andExpect(jsonPath("$[1].title").value("Test Book 2"));

        verify(bookService, times(1)).getAllBooks();
    }

    @Test
    void testDeleteBook() throws Exception {
        doNothing().when(bookService).deleteBook(1L);

        mockMvc.perform(delete("/api/books/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Book deleted successfully"));

        verify(bookService, times(1)).deleteBook(1L);
    }
}
