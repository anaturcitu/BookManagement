package com.unibuc.bookmanagement.integration_test;

import com.unibuc.bookmanagement.models.Book;
import com.unibuc.bookmanagement.repositories.BookRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

@SpringBootTest
@ActiveProfiles("test") // Folosește application-test.properties
@TestPropertySource(properties = "spring.main.allow-bean-definition-overriding=true")

public class BookIntegrationTest {

    @Autowired
    private BookRepository bookRepository;

    @Test
    public void testAddAndRetrieveBook() {
        Book book = new Book();
        book.setTitle("Test Title");
        book.setAuthorId(1L);
        book.setDescription("Test Description");
        book.setIsbn("1234567890123");

        bookRepository.save(book);

        List<Book> books = bookRepository.findAll();
        assertThat(books).isNotEmpty();
        assertThat(books.get(0).getTitle()).isEqualTo("Test Title");
    }
}


