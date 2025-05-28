package com.unibuc.bookmanagement.integration_test;

import com.unibuc.bookmanagement.config.TestSecurityConfig;
import com.unibuc.bookmanagement.repositories.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@Import({TestSecurityConfig.class})
@ActiveProfiles("test")

public class BookControllerWebTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BookRepository bookRepository;

    @BeforeEach
    void cleanDb() {
        bookRepository.deleteAll();
    }

    @Test
    void testCreateBookThroughForm() throws Exception {
        mockMvc.perform(post("/books/add")
                .param("title", "Integration Test Book")
                .param("isbn", "1234567890123")
                .param("description", "A test book")
                .param("authorId", "1")
                .with(csrf())
                .with(user("testuser").roles("USER")))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/books"));

        assertThat(bookRepository.count()).isEqualTo(1);
        assertThat(bookRepository.findAll().get(0).getTitle()).isEqualTo("Integration Test Book");
    }

    @Test
    void testGetAllBooksReturnsView() throws Exception {
        mockMvc.perform(get("/books"))
                .andExpect(status().isOk())
                .andExpect(view().name("book_list"));
    }
}
