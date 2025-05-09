package com.unibuc.bookmanagement.controllers;

import com.unibuc.bookmanagement.models.Author;
import com.unibuc.bookmanagement.services.AuthorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/authors")
public class AuthorController {
    @Autowired
    private AuthorService authorService;

    // creeaza un autor nou
    @PostMapping
    public ResponseEntity<Author> createAuthor(@Valid @RequestBody Author author) {
        Author createdAuthor = authorService.createAuthor(author);
        log.info("Autor creat: {} (data nașterii: {})", createdAuthor.getName(), createdAuthor.getBirth_date());
        return ResponseEntity.ok(createdAuthor);
    }

    // obtine un autor dupa id
    @GetMapping("/{id}")
    public ResponseEntity<Author> getAuthorById(@PathVariable Long id) {
        log.info("Cerere: obținere autor cu ID {}", id);
        Optional<Author> author = authorService.getAuthorById(id);
        if (author.isPresent()) {
            log.info("Autor găsit: {}", author.get().getName());
            return ResponseEntity.ok(author.get());
        } else {
            log.warn("Autorul cu ID-ul {} nu a fost găsit.", id);
            return ResponseEntity.notFound().build();
        }
    }

    // obtine toti autorii
    @GetMapping
    public ResponseEntity<List<Author>> getAllAuthors() {
        List<Author> authors = authorService.getAllAuthors();
        log.info("S-au returnat {} autori.", authors.size());
        return ResponseEntity.ok(authors);
    }

    // sterge un autor dupa id
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAuthor(@PathVariable Long id) {
        log.info("Cerere: ștergere autor cu ID {}", id);
        authorService.deleteAuthor(id);
        log.info("Autorul cu ID {} a fost șters.", id);
        return ResponseEntity.ok("Author deleted successfully");
    }
}
