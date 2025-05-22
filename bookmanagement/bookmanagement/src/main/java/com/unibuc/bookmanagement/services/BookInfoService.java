package com.unibuc.bookmanagement.services;

import com.unibuc.bookmanagement.models.BookInfo;
import com.unibuc.bookmanagement.repositories.BookInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookInfoService {

    @Autowired
    private BookInfoRepository bookInfoRepository;

    public BookInfo saveBookInfo(BookInfo bookInfo) {
        return bookInfoRepository.save(bookInfo);
    }

    public BookInfo getByBookId(Long bookId) {
        return bookInfoRepository.findByBookId(bookId);
    }
}
