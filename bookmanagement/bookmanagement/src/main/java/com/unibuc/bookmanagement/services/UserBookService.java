package com.unibuc.bookmanagement.services;

import com.unibuc.bookmanagement.models.UserBook;
import com.unibuc.bookmanagement.repositories.UserBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserBookService {
    @Autowired
    private UserBookRepository userBookRepository;

    public UserBook addUserBook(UserBook userBook) {
        return userBookRepository.save(userBook);
    }

    public List<UserBook> getUserBooks(Long userId) {
        return userBookRepository.findByUserId(userId);
    }

    public List<UserBook> getBookUsers(Long bookId) {
        return userBookRepository.findByBookId(bookId);
    }
}
