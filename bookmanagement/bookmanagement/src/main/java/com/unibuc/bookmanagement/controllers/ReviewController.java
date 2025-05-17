package com.unibuc.bookmanagement.controllers;

import com.unibuc.bookmanagement.models.Book;
import com.unibuc.bookmanagement.models.Review;
import com.unibuc.bookmanagement.models.User;
import com.unibuc.bookmanagement.services.BookService;
import com.unibuc.bookmanagement.services.ReviewService;
import com.unibuc.bookmanagement.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/reviews")
public class ReviewController {
    @Autowired
    private ReviewService reviewService;

    @Autowired
    private UserService userService;

    @Autowired
    private BookService bookService;

    @GetMapping
    public List<Review> getAllReviews() {
        return reviewService.getAllReviews();
    }

    @GetMapping("/book/{id}")
    public String viewReviewsForBook(@PathVariable Long id, Model model) {
        Book book = bookService.getBookById(id).orElseThrow(() -> new RuntimeException("Cartea nu există"));
        List<Review> reviews = reviewService.getReviewsByBookId(id);

        model.addAttribute("book", book);
        model.addAttribute("reviews", reviews);
        return "view-reviews";
    }

    @GetMapping("/add/{id}")
    public String showAddReviewForm(@PathVariable Long id, Model model) {
        Book book = bookService.getBookById(id)
                .orElseThrow(() -> new RuntimeException("Cartea nu există"));

        model.addAttribute("book", book);
        return "add-review";
    }

    @PostMapping("/add")
    public String addReview(@RequestParam Long bookId, @RequestParam String content, @RequestParam Integer rating, Authentication authentication) {
        // obtine utilizatorul autentificat din SecurityContext
        org.springframework.security.core.userdetails.User springUser = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();

        User user = userService.findByUsername(springUser.getUsername())
                .orElseThrow(() -> new RuntimeException("Utilizatorul nu a fost găsit"));

        // creeaza recenzia:
        Review review = new Review();
        review.setContent(content);
        review.setRating(rating);
        review.setBook(bookService.getBookById(bookId).orElseThrow(() -> new RuntimeException("Cartea nu există")));
        review.setUser(user);

        // salveaza recenzia in baza de date:
        reviewService.createReview(review);

        return "redirect:/reviews/book/" + bookId;
    }
}
