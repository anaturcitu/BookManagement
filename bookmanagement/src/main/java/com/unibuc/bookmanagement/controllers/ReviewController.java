package com.unibuc.bookmanagement.controllers;

import com.unibuc.bookmanagement.models.Book;
import com.unibuc.bookmanagement.models.Review;
import com.unibuc.bookmanagement.models.User;
import com.unibuc.bookmanagement.services.BookService;
import com.unibuc.bookmanagement.services.ReviewService;
//import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/reviews")
public class ReviewController {
    @Autowired
    private ReviewService reviewService;

    @Autowired
    private BookService bookService;

    @GetMapping
    public List<Review> getAllReviews() {
        List<Review> reviews = reviewService.getAllReviews();
        log.info("S-au returnat {} recenzii", reviews.size());
        return reviewService.getAllReviews();
    }

    @GetMapping("/book/{id}")
    public String viewReviewsForBook(@PathVariable Long id, Model model) {
        log.info("Se vizualizează recenziile pentru cartea cu ID-ul {}", id);
        Book book = bookService.getBookById(id).orElseThrow(() -> new RuntimeException("Cartea nu există"));
        List<Review> reviews = reviewService.getReviewsByBookId(id);

        model.addAttribute("book", book);
        model.addAttribute("reviews", reviews);
        return "view-reviews";
    }

    @GetMapping("/add/{id}")
    public String showAddReviewForm(@PathVariable Long id, Model model) {
        log.info("Se accesează formularul de adăugare recenzie pentru cartea cu ID-ul: {}", id);
        Book book = bookService.getBookById(id)
                .orElseThrow(() -> new RuntimeException("Cartea nu există"));

        model.addAttribute("book", book);
        return "add-review";
    }

    @PostMapping("/add")
    public String addReview(@RequestParam Long bookId, @RequestParam String content, @RequestParam Integer rating, Authentication authentication) {
        User user = (User) authentication.getPrincipal(); // obtine utilizatorul logat din Authentication
        log.info("Utilizatorul {} adaugă o recenzie pentru cartea cu ID-ul {}", user.getUsername(), bookId);

        // creeaza o instanta de Review:
        Review review = new Review();
        review.setContent(content);
        review.setRating(rating);
        review.setBook(bookService.getBookById(bookId).orElseThrow(() -> new RuntimeException("Cartea nu există")));
        review.setUser(user);  // seteaza utilizatorul care a lasat recenzia

        // salveaza recenzia in baza de date:
        reviewService.createReview(review);

        return "redirect:/reviews/book/" + bookId;
    }

    // Pentru testare directă
    public Review getReviewById(Long id) {
        return reviewService.getReviewById(id);
    }

    public List<Review> getReviewsByBookId(Long bookId) {
        return reviewService.getReviewsByBookId(bookId);
    }

    public List<Review> getReviewsByUserId(Long userId) {
        return reviewService.getReviewsByUserId(userId);
    }

    public Review createReview(Review review) {
        return reviewService.createReview(review);
    }

    public Review updateReview(Long id, Review review) {
        return reviewService.updateReview(id, review);
    }

    public void deleteReview(Long id) {
        reviewService.deleteReview(id);
    }



}
