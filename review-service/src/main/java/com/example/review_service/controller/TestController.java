package com.example.review_service.controller;

import com.example.review_service.dto.BookDTO;
import com.example.review_service.service.ReviewService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    private final ReviewService reviewService;

    public TestController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping("/book/{id}")
    public ResponseEntity<String> testBook(@PathVariable Long id) {
        try {
            reviewService.validateBook(id);
            return ResponseEntity.ok("Cartea cu ID " + id + " este validă");
        } catch (RuntimeException ex) {
            return ResponseEntity
                    .status(HttpStatus.SERVICE_UNAVAILABLE)
                    .body(ex.getMessage());
        }
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<String> testUser(@PathVariable Long id) {
        try {
            reviewService.validateUser(id);                   // ► poate arunca RuntimeException
            return ResponseEntity.ok("Utilizatorul cu ID " + id + " este valid");
        } catch (RuntimeException ex) {
            return ResponseEntity                         // ► 503 în caz de fallback
                    .status(HttpStatus.SERVICE_UNAVAILABLE)
                    .body(ex.getMessage());
        }
}
}
