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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Controller
@RequestMapping("/reviews")
public class ReviewController {

    private static final Logger logger = LoggerFactory.getLogger(ReviewController.class);

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private UserService userService;

    @Autowired
    private BookService bookService;

    @GetMapping
    @ResponseBody
    public List<Review> getAllReviews() {
        logger.info("Se obțin toate recenziile din sistem");
        return reviewService.getAllReviews();
    }

    @GetMapping("/book/{id}")
    public String viewReviewsForBook(@PathVariable Long id,
                                     @RequestParam(defaultValue = "0") int page,
                                     @RequestParam(defaultValue = "5") int size,
                                     Model model) {

        logger.info("Se afișează recenziile pentru cartea cu ID-ul: {}, pagină: {}, dimensiune: {}", id, page, size);

        Book book = bookService.getBookById(id)
                .orElseThrow(() -> new RuntimeException("Cartea nu există"));

        Pageable pageable = PageRequest.of(page, size);
        Page<Review> reviewPage = reviewService.getPaginatedReviewsByBookId(id, pageable);

        model.addAttribute("book", book);
        model.addAttribute("reviews", reviewPage.getContent());
        model.addAttribute("reviewPage", reviewPage);

        return "view-reviews";
    }

    @GetMapping("/add/{id}")
    public String showAddReviewForm(@PathVariable Long id, Model model) {
        logger.info("Se afișează formularul de adăugare recenzie pentru cartea cu ID-ul: {}", id);

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

        logger.info("Se trimite formularul de recenzie pentru cartea cu ID-ul: {}", reviewDTO.getBookId());

        if (bindingResult.hasErrors()) {
            logger.warn("Formularul conține erori: {}", bindingResult.getAllErrors());

            Book book = bookService.getBookById(reviewDTO.getBookId())
                    .orElseThrow(() -> new RuntimeException("Cartea nu există"));
            model.addAttribute("book", book);
            return "add-review";
        }

        org.springframework.security.core.userdetails.User springUser =
                (org.springframework.security.core.userdetails.User) authentication.getPrincipal();

        User user = userService.findByUsername(springUser.getUsername())
                .orElseThrow(() -> new RuntimeException("Utilizatorul nu a fost găsit"));

        Book book = bookService.getBookById(reviewDTO.getBookId())
                .orElseThrow(() -> new RuntimeException("Cartea nu există"));

        Review review = new Review();
        review.setContent(reviewDTO.getContent());
        review.setRating(reviewDTO.getRating());
        review.setBook(book);
        review.setUser(user);

        logger.info("Se salvează recenzia pentru utilizatorul '{}' și cartea cu ID-ul: {}", user.getUsername(), book.getId());

        reviewService.createReview(review);

        return "redirect:/reviews/book/" + reviewDTO.getBookId();
    }
}
