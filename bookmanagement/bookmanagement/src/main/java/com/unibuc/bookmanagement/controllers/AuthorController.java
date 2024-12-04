package com.unibuc.bookmanagement.controllers;

import com.unibuc.bookmanagement.models.Author;
import com.unibuc.bookmanagement.services.AuthorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/authors")
public class AuthorController {
    @Autowired
    private AuthorService authorService;

    // creeaza un autor nou
    @PostMapping
    public ResponseEntity<Author> createAuthor(@Valid @RequestBody Author author) {
        Author createdAuthor = authorService.createAuthor(author);
        return ResponseEntity.ok(createdAuthor);
    }

    // obtine un autor dupa id
    @GetMapping("/{id}")
    public ResponseEntity<Author> getAuthorById(@PathVariable Long id) {
        Optional<Author> author = authorService.getAuthorById(id);
        return author.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    // obtine toti autorii
    @GetMapping
    public ResponseEntity<List<Author>> getAllAuthors() {
        List<Author> authors = authorService.getAllAuthors();
        return ResponseEntity.ok(authors);
    }

    // sterge un autor dupa id
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAuthor(@PathVariable Long id) {
        authorService.deleteAuthor(id);
        return ResponseEntity.ok("Author deleted successfully");
    }
}
