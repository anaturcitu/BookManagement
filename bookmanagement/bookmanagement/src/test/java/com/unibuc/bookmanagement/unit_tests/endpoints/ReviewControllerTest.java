package com.unibuc.bookmanagement.unit_tests.endpoints;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.web.servlet.MockMvc;

import com.unibuc.bookmanagement.controllers.ReviewController;
import com.unibuc.bookmanagement.models.Book;
import com.unibuc.bookmanagement.models.Review;
import com.unibuc.bookmanagement.models.User;
import com.unibuc.bookmanagement.services.BookService;
import com.unibuc.bookmanagement.services.ReviewService;
import com.unibuc.bookmanagement.services.UserService;


@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(ReviewController.class)
public class ReviewControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReviewService reviewService;

    @MockBean
    private BookService bookService;

    @MockBean
    private UserService userService;  // <== Adaugă asta!

    @Test
public void testViewReviewsForBook() throws Exception {
    Book book = new Book();
    book.setId(1L);

    User user = new User();
    user.setUsername("testuser");

    Review review = new Review();
    review.setId(1L);
    review.setBook(book);
    review.setUser(user);  // <== asta trebuie adăugat

    when(bookService.getBookById(1L)).thenReturn(Optional.of(book));
    when(reviewService.getPaginatedReviewsByBookId(anyLong(), any()))
        .thenReturn(new PageImpl<>(List.of(review), PageRequest.of(0, 5), 1));

    mockMvc.perform(get("/reviews/book/1"))
        .andExpect(status().isOk())
        .andExpect(view().name("view-reviews"))
        .andExpect(model().attributeExists("book"))
        .andExpect(model().attributeExists("reviews"))
        .andExpect(model().attributeExists("reviewPage"));
}

}

