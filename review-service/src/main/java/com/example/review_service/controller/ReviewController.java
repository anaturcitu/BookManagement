package com.example.review_service.controller;

import com.example.review_service.dto.ReviewDTO;
import com.example.review_service.model.Review;
import com.example.review_service.service.ReviewService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    private static final Logger logger = LoggerFactory.getLogger(ReviewController.class);

    @Autowired
    private ReviewService reviewService;

    @PostMapping
    public ResponseEntity<Review> createReview(@Valid @RequestBody ReviewDTO reviewDTO) {
        logger.info("Se primește o cerere POST pentru recenzie");

        // mapare DTO → entitate
        Review review = new Review();
        review.setContent(reviewDTO.getContent());
        review.setRating(reviewDTO.getRating());
        review.setUserId(reviewDTO.getUserId());
        review.setBookId(reviewDTO.getBookId());

        Review savedReview = reviewService.createReview(review);
        return ResponseEntity.ok(savedReview);
    }

    @GetMapping
    public ResponseEntity<List<Review>> getAllReviews() {
        return ResponseEntity.ok(reviewService.getAllReviews());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Review> getReviewById(@PathVariable Long id) {
        return ResponseEntity.ok(reviewService.getReviewById(id));
    }

    @GetMapping("/book/{bookId}")
    public ResponseEntity<List<Review>> getReviewsByBookId(@PathVariable Long bookId) {
        return ResponseEntity.ok(reviewService.getReviewsByBookId(bookId));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Review>> getReviewsByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(reviewService.getReviewsByUserId(userId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Review> updateReview(@PathVariable Long id, @Valid @RequestBody ReviewDTO reviewDTO) {
        Review updated = new Review();
        updated.setContent(reviewDTO.getContent());
        updated.setRating(reviewDTO.getRating());
        return ResponseEntity.ok(reviewService.updateReview(id, updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long id) {
        reviewService.deleteReview(id);
        return ResponseEntity.noContent().build();
    }
}
