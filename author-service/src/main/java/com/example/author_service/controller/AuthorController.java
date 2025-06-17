package com.example.author_service.controller;

import com.example.author_service.dto.AuthorDTO;
import com.example.author_service.model.Author;
import com.example.author_service.service.AuthorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/authors")
public class AuthorController {

    private static final Logger logger = LoggerFactory.getLogger(AuthorController.class);

    @Autowired
    private AuthorService authorService;

    @GetMapping
    public List<Author> getAllAuthors() {
        logger.info("GET /authors - Fetching all authors");
        return authorService.getAllAuthors();
    }

    @GetMapping("/{id}")
    public Optional<Author> getAuthorById(@PathVariable Long id) {
        logger.info("GET /authors/{} - Fetching author by ID", id);
        return authorService.getAuthorById(id);
    }

    //    @PostMapping
    //    public Author createAuthor(@RequestBody AuthorDTO authorDTO) {
    //        return authorService.createAuthor(authorDTO);
    //    }

    @PostMapping
    public ResponseEntity<Author> createAuthor(@Valid @RequestBody AuthorDTO authorDTO) {
        logger.info("POST /authors - Creating author: {}", authorDTO.getName());
        Author saved = authorService.createAuthor(authorDTO);
        logger.info("Author created successfully with ID: {}", saved.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @DeleteMapping("/{id}")
    public void deleteAuthor(@PathVariable Long id) {
        logger.info("DELETE /authors/{} - Deleting author", id);
        authorService.deleteAuthor(id);
        logger.info("Author with ID {} deleted successfully", id);
    }

    @GetMapping("/search")
    public Optional<Author> getAuthorByName(@RequestParam String name) {
        logger.info("GET /authors/search?name={} - Searching author by name", name);
        return authorService.getAuthorByName(name);
    }
}
