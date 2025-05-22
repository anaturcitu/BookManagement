package com.unibuc.bookmanagement.repositories;

import com.unibuc.bookmanagement.models.BookInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookInfoRepository extends JpaRepository<BookInfo, Long> {
    BookInfo findByBookId(Long bookId);
}
