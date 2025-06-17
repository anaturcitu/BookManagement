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
        logger.info("GET /userbooks - Se obțin toate relațiile utilizator-carte.");
        List<UserBook> allUserBooks = userBookService.getAllUserBooks();
        logger.info("S-au returnat {} relații utilizator-carte", allUserBooks.size());
        return ResponseEntity.ok(allUserBooks);
    }

    @PostMapping
    public ResponseEntity<UserBook> addUserBook(@RequestBody UserBook userBook) {
        logger.info("POST /userbooks - Se adaugă o nouă relație utilizator-carte: userId={}, bookId={}",
                userBook.getUserId(), userBook.getBookId());

        // NU mai e nevoie să setezi manual cheia compusă
        UserBook createdUserBook = userBookService.addUserBook(userBook);
        logger.info("Relația utilizator-carte a fost salvată cu succes");
        return ResponseEntity.ok(createdUserBook);
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<List<UserBook>> getUserBooks(@PathVariable Long userId) {
        logger.info("GET /userbooks/users/{} - Se obțin toate cărțile asociate utilizatorului", userId);
        List<UserBook> userBooks = userBookService.getUserBooks(userId);
        logger.info("Utilizatorul cu ID-ul {} are asociate {} cărți", userId, userBooks.size());
        return ResponseEntity.ok(userBooks);
    }

    @GetMapping("/books/{bookId}")
    public ResponseEntity<List<UserBook>> getBookUsers(@PathVariable Long bookId) {
        logger.info("GET /userbooks/books/{} - Se obțin toți utilizatorii care au asociat cartea", bookId);
        List<UserBook> bookUsers = userBookService.getBookUsers(bookId);
        logger.info("Cartea cu ID-ul {} este asociată cu {} utilizatori", bookId, bookUsers.size());
        return ResponseEntity.ok(bookUsers);
    }
}
