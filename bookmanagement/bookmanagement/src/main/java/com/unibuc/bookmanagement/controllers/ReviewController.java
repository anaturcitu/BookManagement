package com.unibuc.bookmanagement.controllers;

import com.unibuc.bookmanagement.dto.ReviewDTO;
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
import org.springframework.validation.BindingResult;
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

        ReviewDTO reviewDTO = new ReviewDTO();
        reviewDTO.setBookId(id);  // foarte important!

        model.addAttribute("book", book);
        model.addAttribute("reviewDTO", reviewDTO);
        return "add-review";
    }

    @PostMapping("/add")
    public String addReview(@Valid @ModelAttribute("reviewDTO") ReviewDTO reviewDTO,
                            BindingResult bindingResult,
                            Authentication authentication,
                            Model model) {
        if (bindingResult.hasErrors()) {
            // In caz de erori, refacem datele pentru view
            Book book = bookService.getBookById(reviewDTO.getBookId())
                    .orElseThrow(() -> new RuntimeException("Cartea nu există"));
            model.addAttribute("book", book);
            return "add-review"; // revenim la formular cu erorile afisate
        }

        org.springframework.security.core.userdetails.User springUser = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();

        User user = userService.findByUsername(springUser.getUsername())
                .orElseThrow(() -> new RuntimeException("Utilizatorul nu a fost găsit"));

        Book book = bookService.getBookById(reviewDTO.getBookId())
                .orElseThrow(() -> new RuntimeException("Cartea nu există"));

        Review review = new Review();
        review.setContent(reviewDTO.getContent());
        review.setRating(reviewDTO.getRating());
        review.setBook(book);
        review.setUser(user);

        reviewService.createReview(review);

        return "redirect:/reviews/book/" + reviewDTO.getBookId();
    }

}
