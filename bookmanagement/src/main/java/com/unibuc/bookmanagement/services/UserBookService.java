package com.unibuc.bookmanagement.services;

import com.unibuc.bookmanagement.models.UserBook;
import com.unibuc.bookmanagement.repositories.UserBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@Service
public class UserBookService {

    @Autowired
    private UserBookRepository userBookRepository;

    public UserBook addUserBook(UserBook userBook) {
        log.info("Salvare asociere user ID {} - carte ID {}", 
                 userBook.getUser().getId(), userBook.getBook().getId());
        return userBookRepository.save(userBook);
    }

    public List<UserBook> getUserBooks(Long userId) {
        log.info("Se obțin cărțile asociate userului cu ID {}", userId);
        return userBookRepository.findByUserId(userId);
    }

    public List<UserBook> getBookUsers(Long bookId) {
        log.info("Se obțin utilizatorii care au cartea cu ID {}", bookId);
        return userBookRepository.findByBookId(bookId);
    }
}
