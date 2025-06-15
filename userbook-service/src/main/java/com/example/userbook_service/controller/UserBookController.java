package com.example.userbook_service.controller;

import com.example.userbook_service.model.UserBook;
import com.example.userbook_service.service.UserBookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/userbooks")
public class UserBookController {

    private static final Logger logger = LoggerFactory.getLogger(UserBookController.class);

    @Autowired
    private UserBookService userBookService;

    @GetMapping
    public ResponseEntity<List<UserBook>> getAllUserBooks() {
        logger.info("Se obțin toate relațiile utilizator-carte.");
        List<UserBook> allUserBooks = userBookService.getAllUserBooks();
        return ResponseEntity.ok(allUserBooks);
    }

    @PostMapping
    public ResponseEntity<UserBook> addUserBook(@RequestBody UserBook userBook) {
        logger.info("Se adaugă o nouă relație utilizator-carte: userId={}, bookId={}",
                userBook.getUserId(), userBook.getBookId());

        // NU mai e nevoie să setezi manual cheia compusă
        UserBook createdUserBook = userBookService.addUserBook(userBook);
        return ResponseEntity.ok(createdUserBook);
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<List<UserBook>> getUserBooks(@PathVariable Long userId) {
        logger.info("Se obțin toate cărțile asociate utilizatorului cu ID-ul: {}", userId);
        List<UserBook> userBooks = userBookService.getUserBooks(userId);
        return ResponseEntity.ok(userBooks);
    }

    @GetMapping("/books/{bookId}")
    public ResponseEntity<List<UserBook>> getBookUsers(@PathVariable Long bookId) {
        logger.info("Se obțin toți utilizatorii care au asociat cartea cu ID-ul: {}", bookId);
        List<UserBook> bookUsers = userBookService.getBookUsers(bookId);
        return ResponseEntity.ok(bookUsers);
    }
}
