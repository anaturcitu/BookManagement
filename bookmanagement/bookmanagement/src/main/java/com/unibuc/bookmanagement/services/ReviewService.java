package com.unibuc.bookmanagement.services;

import com.unibuc.bookmanagement.models.Review;
import com.unibuc.bookmanagement.repositories.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Service
public class ReviewService {

    private static final Logger logger = LoggerFactory.getLogger(ReviewService.class);

    @Autowired
    private ReviewRepository reviewRepository;

    public List<Review> getAllReviews() {
        logger.info("Se obțin toate recenziile din bază");
        return reviewRepository.findAll();
    }

    public Review getReviewById(Long id) {
        logger.info("Se caută recenzia cu ID-ul: {}", id);
        return reviewRepository.findById(id).orElseThrow(() -> new RuntimeException("Review not found"));
    }

    public List<Review> getReviewsByBookId(Long bookId) {
        logger.info("Se obțin recenziile pentru cartea cu ID-ul: {}", bookId);
        return reviewRepository.findByBookId(bookId);
    }

    public List<Review> getReviewsByUserId(Long userId) {
        logger.info("Se obțin recenziile utilizatorului cu ID-ul: {}", userId);
        return reviewRepository.findByUserId(userId);
    }

    public Review createReview(Review review) {
        logger.info("Se creează o recenzie nouă pentru cartea cu ID-ul: {}", review.getBook().getId());
        review.setCreatedAt(java.time.LocalDateTime.now());
        return reviewRepository.save(review);
    }

    public Review updateReview(Long id, Review updatedReview) {
        logger.info("Se actualizează recenzia cu ID-ul: {}", id);
        Review review = getReviewById(id);
        review.setContent(updatedReview.getContent());
        review.setRating(updatedReview.getRating());
        return reviewRepository.save(review);
    }

    public void deleteReview(Long id) {
        logger.info("Se șterge recenzia cu ID-ul: {}", id);
        reviewRepository.deleteById(id);
    }

    // pentru paginare:
    public Page<Review> getPaginatedReviewsByBookId(Long bookId, Pageable pageable) {
        logger.info("Se obțin recenziile paginabile pentru cartea cu ID-ul: {}", bookId);
        return reviewRepository.findByBookId(bookId, pageable);
    }

}
