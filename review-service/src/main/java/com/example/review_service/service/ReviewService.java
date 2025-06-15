package com.example.review_service.service;

import com.example.review_service.feignClient.BookClient;
import com.example.review_service.feignClient.UserClient;
import com.example.review_service.model.Review;
import com.example.review_service.repository.ReviewRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReviewService {

    private static final Logger logger = LoggerFactory.getLogger(ReviewService.class);

    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private UserClient userClient;

    @Autowired
    private BookClient bookClient;

    public Review createReview(Review review) {
        logger.info("Se creează o recenzie nouă pentru cartea cu ID-ul: {}", review.getBookId());

        // validam ca user-ul si cartea exista
        userClient.getUserById(review.getUserId());
        bookClient.getBookById(review.getBookId());

        review.setCreatedAt(LocalDateTime.now());
        return reviewRepository.save(review);
    }

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

}
