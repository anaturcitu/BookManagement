package com.unibuc.bookmanagement.repositories;

import com.unibuc.bookmanagement.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
}
