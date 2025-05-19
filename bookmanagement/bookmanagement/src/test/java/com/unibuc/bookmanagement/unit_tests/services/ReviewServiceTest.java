package com.unibuc.bookmanagement.unit_tests.services;

import com.unibuc.bookmanagement.models.Book;
import com.unibuc.bookmanagement.models.Review;
import com.unibuc.bookmanagement.repositories.ReviewRepository;
import com.unibuc.bookmanagement.services.ReviewService;
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
public class ReviewServiceTest {
    @Mock
    private ReviewRepository reviewRepository;

    @InjectMocks
    private ReviewService reviewService;

    @Test
    public void testGetAllReviews() {
        List<Review> reviews = Arrays.asList(new Review(), new Review());
        when(reviewRepository.findAll()).thenReturn(reviews);

        List<Review> result = reviewService.getAllReviews();

        assertEquals(2, result.size());
        verify(reviewRepository, times(1)).findAll();
    }

    @Test
    public void testGetReviewById() {
        Review review = new Review();
        review.setId(1L);
        when(reviewRepository.findById(1L)).thenReturn(Optional.of(review));

        Review result = reviewService.getReviewById(1L);

        assertEquals(1L, result.getId());
        verify(reviewRepository, times(1)).findById(1L);
    }

    @Test
    public void testGetReviewByIdNotFound() {
        when(reviewRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            reviewService.getReviewById(1L);
        });

        assertEquals("Review not found", exception.getMessage());
        verify(reviewRepository, times(1)).findById(1L);
    }

    @Test
    public void testGetReviewsByBookId() {
        List<Review> reviews = Arrays.asList(new Review(), new Review());
        when(reviewRepository.findByBookId(1L)).thenReturn(reviews);

        List<Review> result = reviewService.getReviewsByBookId(1L);

        assertEquals(2, result.size());
        verify(reviewRepository, times(1)).findByBookId(1L);
    }

    @Test
    public void testGetReviewsByUserId() {
        List<Review> reviews = Arrays.asList(new Review(), new Review());
        when(reviewRepository.findByUserId(1L)).thenReturn(reviews);

        List<Review> result = reviewService.getReviewsByUserId(1L);

        assertEquals(2, result.size());
        verify(reviewRepository, times(1)).findByUserId(1L);
    }

    @Test
    public void testCreateReview() {
        Review review = new Review();
        review.setContent("Great book");
        review.setRating(5);

        Book mockBook = new Book();
        mockBook.setId(42L);
        review.setBook(mockBook);

        when(reviewRepository.save(any(Review.class))).thenAnswer(invocation -> {
            Review input = invocation.getArgument(0);
            input.setId(1L);
            return input;
        });

        Review result = reviewService.createReview(review);

        assertNotNull(result.getCreatedAt());
        assertEquals(1L, result.getId());
        assertEquals("Great book", result.getContent());
        verify(reviewRepository, times(1)).save(any(Review.class));
    }

    @Test
    public void testUpdateReview() {
        Review existingReview = new Review();
        existingReview.setId(1L);
        existingReview.setContent("Old content");
        existingReview.setRating(3);

        Review updatedReview = new Review();
        updatedReview.setContent("New content");
        updatedReview.setRating(5);

        when(reviewRepository.findById(1L)).thenReturn(Optional.of(existingReview));
        when(reviewRepository.save(existingReview)).thenReturn(existingReview);

        Review result = reviewService.updateReview(1L, updatedReview);

        assertEquals("New content", result.getContent());
        assertEquals(5, result.getRating());
        verify(reviewRepository, times(1)).findById(1L);
        verify(reviewRepository, times(1)).save(existingReview);
    }

    @Test
    public void testDeleteReview() {
        doNothing().when(reviewRepository).deleteById(1L);

        reviewService.deleteReview(1L);

        verify(reviewRepository, times(1)).deleteById(1L);
    }
}
