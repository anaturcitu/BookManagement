package com.unibuc.bookmanagement.unit_tests.endpoints;

import com.unibuc.bookmanagement.controllers.UserBookController;
import com.unibuc.bookmanagement.junction_tables.UserBookId;
import com.unibuc.bookmanagement.models.Book;
import com.unibuc.bookmanagement.models.User;
import com.unibuc.bookmanagement.models.UserBook;
import com.unibuc.bookmanagement.services.UserBookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(UserBookController.class)
@AutoConfigureMockMvc(addFilters = false)
public class UserBookControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserBookService userBookService;

    private UserBook userBook;
    private User user;
    private Book book;
    private UserBookId userBookId;

    @BeforeEach
    public void setUp() {
        user = new User();
        book = new Book();
        userBookId = new UserBookId(1L, 1L);
        userBook = new UserBook(userBookId, "READING", LocalDateTime.now(), user, book);
    }

    @Test
    public void testAddUserBook() throws Exception {
        when(userBookService.addUserBook(Mockito.any(UserBook.class))).thenReturn(userBook);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/userbooks")
                        .contentType("application/json")
                        .content("{\"id\":{\"userId\":1,\"bookId\":1},\"status\":\"READING\",\"addedAt\":\"2024-12-11T10:00:00\",\"user\":{},\"book\":{}}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("READING"));

        verify(userBookService, times(1)).addUserBook(Mockito.any(UserBook.class));
    }

    @Test
    public void testGetUserBooks() throws Exception {
        List<UserBook> userBooks = Arrays.asList(userBook);
        when(userBookService.getUserBooks(1L)).thenReturn(userBooks);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/userbooks/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].status").value("READING"));

        verify(userBookService, times(1)).getUserBooks(1L);
    }

    @Test
    public void testGetBookUsers() throws Exception {
        List<UserBook> bookUsers = Arrays.asList(userBook);
        when(userBookService.getBookUsers(1L)).thenReturn(bookUsers);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/userbooks/books/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].status").value("READING"));

        verify(userBookService, times(1)).getBookUsers(1L);
    }
}
