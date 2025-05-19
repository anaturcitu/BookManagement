package com.unibuc.bookmanagement.services;

import com.unibuc.bookmanagement.models.UserBook;
import com.unibuc.bookmanagement.repositories.UserBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Service
public class UserBookService {

    private static final Logger logger = LoggerFactory.getLogger(UserBookService.class);

    @Autowired
    private UserBookRepository userBookRepository;

    public UserBook addUserBook(UserBook userBook) {
        logger.info("Se adaugă o nouă relație între utilizatorul cu ID-ul {} și cartea cu ID-ul {}",
                userBook.getUser().getId(), userBook.getBook().getId());
        return userBookRepository.save(userBook);
    }

    public List<UserBook> getUserBooks(Long userId) {
        logger.info("Se obțin cărțile adăugate de utilizatorul cu ID-ul: {}", userId);
        return userBookRepository.findByUserId(userId);
    }

    public List<UserBook> getBookUsers(Long bookId) {
        logger.info("Se obțin utilizatorii care au adăugat cartea cu ID-ul: {}", bookId);
        return userBookRepository.findByBookId(bookId);
    }
}
