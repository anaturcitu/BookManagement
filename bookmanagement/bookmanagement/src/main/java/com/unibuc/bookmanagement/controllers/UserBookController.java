package com.unibuc.bookmanagement.controllers;

import com.unibuc.bookmanagement.models.UserBook;
import com.unibuc.bookmanagement.services.UserBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/userbooks")
public class UserBookController {
    @Autowired
    private UserBookService userBookService;

    @PostMapping
    public ResponseEntity<UserBook> addUserBook(@RequestBody UserBook userBook) {
        UserBook createdUserBook = userBookService.addUserBook(userBook);
        return ResponseEntity.ok(createdUserBook);
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<List<UserBook>> getUserBooks(@PathVariable Long userId) {
        List<UserBook> userBooks = userBookService.getUserBooks(userId);
        return ResponseEntity.ok((userBooks));
    }

    @GetMapping("/books/{bookId}")
    public ResponseEntity<List<UserBook>> getBookUsers(@PathVariable Long bookId) {
        List<UserBook> bookUsers = userBookService.getBookUsers(bookId);
        return ResponseEntity.ok(bookUsers);
    }
}
