package com.unibuc.bookmanagement.controllers;

import com.unibuc.bookmanagement.models.Author;
import com.unibuc.bookmanagement.services.AuthorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/authors")
public class AuthorController {

    private static final Logger logger = LoggerFactory.getLogger(AuthorController.class);

    @Autowired
    private AuthorService authorService;

    // creeaza un autor nou
    @PostMapping
    public ResponseEntity<Author> createAuthor(@Valid @RequestBody Author author) {
        logger.info("Cerere de creare autor: {}", author.getName());
        Author createdAuthor = authorService.createAuthor(author);
        return ResponseEntity.ok(createdAuthor);
    }

    // obtine un autor dupa id
    @GetMapping("/{id}")
    public ResponseEntity<Author> getAuthorById(@PathVariable Long id) {
        logger.info("Cerere de obținere autor cu ID-ul: {}", id);
        Optional<Author> author = authorService.getAuthorById(id);
        return author.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    // obtine toti autorii
    @GetMapping
    public ResponseEntity<List<Author>> getAllAuthors() {
        logger.info("Cerere de obținere a tuturor autorilor");
        List<Author> authors = authorService.getAllAuthors();
        return ResponseEntity.ok(authors);
    }

    // sterge un autor dupa id
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAuthor(@PathVariable Long id) {
        logger.info("Cerere de ștergere autor cu ID-ul: {}", id);
        authorService.deleteAuthor(id);
        return ResponseEntity.ok("Author deleted successfully");
    }
}
