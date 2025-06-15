package com.example.author_service.controller;

import com.example.author_service.dto.AuthorDTO;
import com.example.author_service.model.Author;
import com.example.author_service.service.AuthorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/authors")
public class AuthorController {

    @Autowired
    private AuthorService authorService;

    @GetMapping
    public List<Author> getAllAuthors() {
        return authorService.getAllAuthors();
    }

    @GetMapping("/{id}")
    public Optional<Author> getAuthorById(@PathVariable Long id) {
        return authorService.getAuthorById(id);
    }

//    @PostMapping
//    public Author createAuthor(@RequestBody AuthorDTO authorDTO) {
//        return authorService.createAuthor(authorDTO);
//    }

    @PostMapping
    public ResponseEntity<Author> createAuthor(@Valid @RequestBody AuthorDTO authorDTO) {
        Author saved = authorService.createAuthor(authorDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }


    @DeleteMapping("/{id}")
    public void deleteAuthor(@PathVariable Long id) {
        authorService.deleteAuthor(id);
    }

    @GetMapping("/search")
    public Optional<Author> getAuthorByName(@RequestParam String name) {
        return authorService.getAuthorByName(name);
    }
}

