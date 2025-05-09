package com.unibuc.bookmanagement.controllers;

import com.unibuc.bookmanagement.models.UserBook;
import com.unibuc.bookmanagement.services.UserBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/userbooks")
public class UserBookController {

    @Autowired
    private UserBookService userBookService;

    @PostMapping
    public ResponseEntity<UserBook> addUserBook(@RequestBody UserBook userBook) {
        log.info("Se adaugă asocierea userului cu ID {} și cartea cu ID {}", userBook.getUser().getId(), userBook.getBook().getId());
        UserBook createdUserBook = userBookService.addUserBook(userBook);
        log.info("Asocierea a fost creată cu ID: {}", createdUserBook.getId());
        return ResponseEntity.ok(createdUserBook);
    }


    @GetMapping("/users/{userId}")
    public ResponseEntity<List<UserBook>> getUserBooks(@PathVariable Long userId) {
        log.info("Se cer toate cărțile asociate userului cu ID-ul: {}", userId);
        List<UserBook> userBooks = userBookService.getUserBooks(userId);
        log.info("S-au returnat {} cărți pentru userul cu ID {}", userBooks.size(), userId);
        return ResponseEntity.ok((userBooks));
    }

    @GetMapping("/books/{bookId}")
    public ResponseEntity<List<UserBook>> getBookUsers(@PathVariable Long bookId) {
        log.info("Se cer toți utilizatorii care au cartea cu ID {}", bookId);
        List<UserBook> bookUsers = userBookService.getBookUsers(bookId);
        log.info("S-au returnat {} utilizatori pentru cartea cu ID {}", bookUsers.size(), bookId);
        return ResponseEntity.ok(bookUsers);
    }
}
