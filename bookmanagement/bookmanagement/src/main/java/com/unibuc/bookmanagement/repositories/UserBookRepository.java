package com.unibuc.bookmanagement.repositories;

import com.unibuc.bookmanagement.junction_tables.UserBookId;
import com.unibuc.bookmanagement.models.UserBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserBookRepository extends JpaRepository<UserBook, UserBookId> {
    List<UserBook> findByUserId(Long userId);
    List<UserBook> findByBookId(Long bookId);
}
