package com.unibuc.bookmanagement.services;

import com.unibuc.bookmanagement.models.Review;
import com.unibuc.bookmanagement.repositories.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
//import java.util.Optional;

@Slf4j
@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    public List<Review> getAllReviews() {
        log.info("Se obține lista tuturor recenziilor.");
        return reviewRepository.findAll();
    }

    public Review getReviewById(Long id) {
        log.info("Se caută recenzia cu ID: {}", id);
        return reviewRepository.findById(id).orElseThrow(() -> new RuntimeException("Review not found"));
    }

    public List<Review> getReviewsByBookId(Long bookId) {
        log.info("Se obțin recenziile pentru cartea cu ID: {}", bookId);
        return reviewRepository.findByBookId(bookId);
    }

    public List<Review> getReviewsByUserId(Long userId) {
        log.info("Se obțin recenziile pentru utilizatorul cu ID: {}", userId);
        return reviewRepository.findByUserId(userId);
    }

    public Review createReview(Review review) {
        log.info("Se creează o nouă recenzie pentru cartea cu ID: {}", review.getBook().getId());
        review.setCreatedAt(java.time.LocalDateTime.now());
        return reviewRepository.save(review);
    }

    public Review updateReview(Long id, Review updatedReview) {
        log.info("Se actualizează recenzia cu ID: {}", id);
        Review review = getReviewById(id);
        review.setContent(updatedReview.getContent());
        review.setRating(updatedReview.getRating());
        return reviewRepository.save(review);
    }

    public void deleteReview(Long id) {
        log.info("Se șterge recenzia cu ID: {}", id);
        reviewRepository.deleteById(id);
    }
}
