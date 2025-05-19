package com.unibuc.bookmanagement.unit_tests.endpoints;

import com.unibuc.bookmanagement.controllers.BookController;
import com.unibuc.bookmanagement.models.Author;
import com.unibuc.bookmanagement.models.Book;
import com.unibuc.bookmanagement.services.AuthorService;
import com.unibuc.bookmanagement.services.BookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookController.class)
@AutoConfigureMockMvc(addFilters = false)
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @MockBean
    private AuthorService authorService;

    @Test
    void testGetAllBooksPaginated() throws Exception {
        Book book1 = new Book();
        book1.setId(1L);
        book1.setTitle("Book A");

        Pageable pageable = PageRequest.of(0, 10, Sort.by("title").ascending());
        Page<Book> page = new PageImpl<>(List.of(book1), pageable, 1);

        when(bookService.getBooks(any(Pageable.class))).thenReturn(page);

        mockMvc.perform(get("/books")
                .param("page", "0")
                .param("size", "10")
                .param("sort", "title")
                .param("dir", "asc"))
                .andExpect(status().isOk())
                .andExpect(view().name("book_list"))
                .andExpect(model().attributeExists("bookPage"));
    }

    @Test
    void testBookDetailsFound() throws Exception {
        Book book = new Book();
        book.setId(1L);
        book.setTitle("Test Book");
        book.setAuthorId(10L);

        Author author = new Author();
        author.setId(10L);
        author.setName("John Doe");

        when(bookService.getBookById(1L)).thenReturn(Optional.of(book));
        when(authorService.getAuthorById(10L)).thenReturn(Optional.of(author));

        mockMvc.perform(get("/books/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("book-details"))
                .andExpect(model().attributeExists("book"));
    }

    @Test
    void testBookDetailsNotFound() throws Exception {
        when(bookService.getBookById(999L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/books/999"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/books"));
    }

    @Test
    void testShowAddBookForm() throws Exception {
        mockMvc.perform(get("/books/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("add_book"))
                .andExpect(model().attributeExists("book"));
    }

    @Test
    void testDeleteBook() throws Exception {
        mockMvc.perform(post("/books/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/books"));
    }

    @Test
    void testFindBookByTitleFound() throws Exception {
        Book book = new Book();
        book.setId(123L);
        book.setTitle("Some Book");

        when(bookService.getBookByTitle("Some Book")).thenReturn(Optional.of(book));

        mockMvc.perform(get("/books/find").param("query", "Some Book"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/books/123"));
    }

    @Test
    void testFindBookByTitleNotFound() throws Exception {
        when(bookService.getBookByTitle("Unknown Book")).thenReturn(Optional.empty());

        mockMvc.perform(get("/books/find").param("query", "Unknown Book"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/books"));
    }
}
