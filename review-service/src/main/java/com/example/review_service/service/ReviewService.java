package com.example.review_service.service;

import com.example.review_service.feignClient.BookClient;
import com.example.review_service.feignClient.UserClient;
import com.example.review_service.model.Review;
import com.example.review_service.repository.ReviewRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReviewService {

    private static final Logger log = LoggerFactory.getLogger(ReviewService.class);

    private final ReviewRepository reviewRepository;
    private final UserClient       userClient;
    private final BookClient       bookClient;

    public ReviewService(ReviewRepository reviewRepository,
                         UserClient userClient,
                         BookClient bookClient) {
        this.reviewRepository = reviewRepository;
        this.userClient       = userClient;
        this.bookClient       = bookClient;
    }

    /* -------------------------------------------------
       VALIDARE UTILIZATOR
       ------------------------------------------------- */

    @CircuitBreaker(name = "user-service", fallbackMethod = "userFallback")
    public void validateUser(Long userId) {
        userClient.getUserById(userId);   // dacă eșuează → userFallback()
    }

    public void userFallback(Long userId, Throwable t) {
        log.error("FALLBACK user-service, userId={}", userId, t);
        throw new ResponseStatusException(
                HttpStatus.SERVICE_UNAVAILABLE,
                "Serviciul de utilizatori este indisponibil");
    }

    /* -------------------------------------------------
       VALIDARE CARTE
       ------------------------------------------------- */

    @CircuitBreaker(name = "book-service", fallbackMethod = "bookFallback")
    public void validateBook(Long bookId) {
        bookClient.getBookById(bookId);   // dacă eșuează → bookFallback()
    }

    public void bookFallback(Long bookId, Throwable t) {
        log.error("FALLBACK book-service, bookId={}", bookId, t);
        throw new ResponseStatusException(
                HttpStatus.SERVICE_UNAVAILABLE,
                "Serviciul de cărți este indisponibil");
    }

    /* -------------------------------------------------
       CRUD RECENZII
       ------------------------------------------------- */

    @Transactional
    public Review createReview(Review review) {
        log.info("Creare recenzie nouă -> carte={}, user={}",
                 review.getBookId(), review.getUserId());

        // 1) validăm dependențele (dacă pică, se aruncă 503 din fallback)
        validateUser(review.getUserId());
        validateBook(review.getBookId());

        // 2) persistăm
        review.setCreatedAt(LocalDateTime.now());
        Review saved = reviewRepository.save(review);

        log.info("Recenzia salvată cu ID={}", saved.getId());
        return saved;
    }

    public List<Review> getAllReviews() {
        log.info("Se obțin toate recenziile");
        return reviewRepository.findAll();
    }

    public Review getReviewById(Long id) {
        log.info("Căutare recenzie ID={}", id);
        return reviewRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Recenzia ID={} nu a fost găsită", id);
                    return new ResponseStatusException(
                            HttpStatus.NOT_FOUND, "Review not found");
                });
    }

    public List<Review> getReviewsByBookId(Long bookId) {
        log.info("Recenzii pentru carte ID={}", bookId);
        return reviewRepository.findByBookId(bookId);
    }

    public List<Review> getReviewsByUserId(Long userId) {
        log.info("Recenzii pentru user ID={}", userId);
        return reviewRepository.findByUserId(userId);
    }

    @Transactional
    public Review updateReview(Long id, Review updatedReview) {
        log.info("Actualizare recenzie ID={}", id);
        Review review = getReviewById(id);
        review.setContent(updatedReview.getContent());
        review.setRating(updatedReview.getRating());
        Review saved = reviewRepository.save(review);
        log.info("Recenzia ID={} a fost actualizată", saved.getId());
        return saved;
    }

    @Transactional
    public void deleteReview(Long id) {
        log.info("Ștergere recenzie ID={}", id);
        reviewRepository.deleteById(id);
        log.info("Recenzia ID={} a fost ștearsă", id);
    }
}
