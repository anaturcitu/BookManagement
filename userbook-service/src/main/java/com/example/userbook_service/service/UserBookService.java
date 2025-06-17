package com.example.userbook_service.service;

import com.example.userbook_service.model.UserBook;
import com.example.userbook_service.repository.UserBookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserBookService {

    private static final Logger logger = LoggerFactory.getLogger(UserBookService.class);

    @Autowired
    private UserBookRepository userBookRepository;

    public List<UserBook> getAllUserBooks() {
        logger.info("Service - Se obțin toate relațiile utilizator-carte din bază de date");
        List<UserBook> result = userBookRepository.findAll();
        logger.info("S-au găsit {} relații", result.size());
        return result;
    }

    public UserBook addUserBook(UserBook userBook) {
        logger.info("Service - Se salvează relația utilizator-carte: userId={}, bookId={}",
                userBook.getUserId(), userBook.getBookId());
        UserBook saved = userBookRepository.save(userBook);
        logger.info("Relația a fost salvată cu succes");
        return saved;
    }

    public List<UserBook> getUserBooks(Long userId) {
        logger.info("Service - Se obțin cărțile asociate utilizatorului cu ID-ul: {}", userId);
        List<UserBook> userBooks = userBookRepository.findByUserId(userId);
        logger.info("Utilizatorul are asociate {} cărți", userBooks.size());
        return userBooks;
    }

    public List<UserBook> getBookUsers(Long bookId) {
        logger.info("Service - Se obțin utilizatorii care au asociat cartea cu ID-ul: {}", bookId);
        List<UserBook> bookUsers = userBookRepository.findByBookId(bookId);
        logger.info("Cartea este asociată cu {} utilizatori", bookUsers.size());
        return bookUsers;
    }
}
