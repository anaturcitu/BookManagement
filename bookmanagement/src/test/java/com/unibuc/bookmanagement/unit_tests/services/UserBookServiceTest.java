package com.unibuc.bookmanagement.unit_tests.services;

import com.unibuc.bookmanagement.junction_tables.UserBookId;
import com.unibuc.bookmanagement.models.Book;
import com.unibuc.bookmanagement.models.User;
import com.unibuc.bookmanagement.models.UserBook;
import com.unibuc.bookmanagement.repositories.UserBookRepository;
import com.unibuc.bookmanagement.services.UserBookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import java.util.List;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.assertFalse;

@ExtendWith(MockitoExtension.class)
public class UserBookServiceTest {
    @Mock
    private UserBookRepository userBookRepository;

    @InjectMocks
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
    public void testAddUserBook() {
        when(userBookRepository.save(userBook)).thenReturn(userBook);

        UserBook result = userBookService.addUserBook(userBook);

        assertNotNull(result);
        assertEquals(userBook.getStatus(), result.getStatus());
        verify(userBookRepository, times(1)).save(userBook); // Verificăm dacă repository-ul a fost apelat o dată
    }

    @Test
    public void testGetUserBooks() {
        List<UserBook> userBooks = Arrays.asList(userBook);
        when(userBookRepository.findByUserId(1L)).thenReturn(userBooks);

        List<UserBook> result = userBookService.getUserBooks(1L);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(userBook, result.get(0));
        verify(userBookRepository, times(1)).findByUserId(1L); // Verificăm apelul la repository
    }

    @Test
    public void testGetBookUsers() {
        List<UserBook> userBooks = Arrays.asList(userBook);
        when(userBookRepository.findByBookId(1L)).thenReturn(userBooks);

        List<UserBook> result = userBookService.getBookUsers(1L);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(userBook, result.get(0));
        verify(userBookRepository, times(1)).findByBookId(1L); // Verificăm apelul la repository
    }
}
