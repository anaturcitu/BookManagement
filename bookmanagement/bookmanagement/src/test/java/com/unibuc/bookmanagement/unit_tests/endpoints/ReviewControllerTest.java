package com.unibuc.bookmanagement.unit_tests.endpoints;

import com.unibuc.bookmanagement.controllers.ReviewController;
import com.unibuc.bookmanagement.models.Review;
import com.unibuc.bookmanagement.services.ReviewService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class ReviewControllerTest {
    @Mock
    private ReviewService reviewService;

    @InjectMocks
    private ReviewController reviewController;

    @Test
    public void testGetAllReviews() {
        List<Review> reviews = Arrays.asList(new Review(), new Review());
        when(reviewService.getAllReviews()).thenReturn(reviews);

        List<Review> result = reviewController.getAllReviews();

        assertEquals(2, result.size());
        verify(reviewService, times(1)).getAllReviews();
    }

    @Test
    public void testGetReviewById() {
        Review review = new Review();
        review.setId(1L);
        when(reviewService.getReviewById(1L)).thenReturn(review);

        ResponseEntity<Review> response = reviewController.getReviewById(1L);

        assertEquals(1L, response.getBody().getId());
        verify(reviewService, times(1)).getReviewById(1L);
    }

    @Test
    public void testGetReviewsByBookId() {
        List<Review> reviews = Arrays.asList(new Review(), new Review());
        when(reviewService.getReviewsByBookId(1L)).thenReturn(reviews);

        List<Review> result = reviewController.getReviewsByBookId(1L);

        assertEquals(2, result.size());
        verify(reviewService, times(1)).getReviewsByBookId(1L);
    }

    @Test
    public void testGetReviewsByUserId() {
        List<Review> reviews = Arrays.asList(new Review(), new Review());
        when(reviewService.getReviewsByUserId(1L)).thenReturn(reviews);

        List<Review> result = reviewController.getReviewsByUserId(1L);

        assertEquals(2, result.size());
        verify(reviewService, times(1)).getReviewsByUserId(1L);
    }

    @Test
    public void testCreateReview() {
        Review review = new Review();
        review.setContent("Great book");
        review.setRating(5);
        when(reviewService.createReview(review)).thenReturn(review);

        ResponseEntity<Review> response = reviewController.createReview(review);

        assertEquals("Great book", response.getBody().getContent());
        assertEquals(5, response.getBody().getRating());
        verify(reviewService, times(1)).createReview(review);
    }

    @Test
    public void testUpdateReview() {
        Review review = new Review();
        review.setContent("Updated content");
        review.setRating(4);
        when(reviewService.updateReview(1L, review)).thenReturn(review);

        ResponseEntity<Review> response = reviewController.updateReview(1L, review);

        assertEquals("Updated content", response.getBody().getContent());
        assertEquals(4, response.getBody().getRating());
        verify(reviewService, times(1)).updateReview(1L, review);
    }

    @Test
    public void testDeleteReview() {
        doNothing().when(reviewService).deleteReview(1L);

        ResponseEntity<Void> response = reviewController.deleteReview(1L);

        assertEquals(204, response.getStatusCodeValue());
        verify(reviewService, times(1)).deleteReview(1L);
    }
}
