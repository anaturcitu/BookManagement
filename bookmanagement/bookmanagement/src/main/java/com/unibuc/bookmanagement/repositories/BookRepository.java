package com.unibuc.bookmanagement.repositories;

import com.unibuc.bookmanagement.models.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    Optional<Book> findByTitle(String title);

//    pentru paginare:
    Page<Book> findAll(Pageable pageable);

}
