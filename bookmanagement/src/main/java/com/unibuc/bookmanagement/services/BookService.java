package com.unibuc.bookmanagement.services;

import com.unibuc.bookmanagement.models.Book;
import com.unibuc.bookmanagement.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public Book createBook(Book book) {
        log.info("Salvare carte: {}", book.getTitle());
        return bookRepository.save(book);
    }

    public Optional<Book> getBookById(Long id) {
        log.info("Căutare carte după ID: {}", id);
        return bookRepository.findById(id);
    }

    public Optional<Book> getBookByTitle(String title) {
        log.info("Căutare carte după titlu: {}", title);
        return bookRepository.findByTitle(title);
    }

    public List<Book> getAllBooks() {
        log.info("Se returnează lista completă de cărți.");
        return bookRepository.findAll();
    }

    public void deleteBook(Long id) {
        log.info("Ștergere carte cu ID: {}", id);
        bookRepository.deleteById(id);
    }
}
