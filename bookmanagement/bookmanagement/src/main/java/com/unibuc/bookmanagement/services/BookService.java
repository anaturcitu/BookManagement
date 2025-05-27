package com.unibuc.bookmanagement.services;

import com.unibuc.bookmanagement.dto.BookDTO;
import com.unibuc.bookmanagement.models.Book;
import com.unibuc.bookmanagement.repositories.BookRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class BookService {
    private static final Logger logger = LoggerFactory.getLogger(BookService.class);
    
    @Autowired
    private BookRepository bookRepository;

    public Book createBook(BookDTO bookDTO) {
        logger.info("Creare carte nouă: {}", bookDTO.getTitle());

        // Conversie DTO -> Entity
        Book book = new Book();
        book.setTitle(bookDTO.getTitle());
        book.setIsbn(bookDTO.getIsbn());
        book.setDescription(bookDTO.getDescription());
        book.setAuthorId(bookDTO.getAuthorId());

        Book savedBook = bookRepository.save(book);
        logger.info("Cartea a fost creată cu ID-ul: {}", savedBook.getId());
        return savedBook;
    }


    public Optional<Book> getBookById(Long id) {
        logger.info("Căutare carte după ID: {}", id);
        Optional<Book> book = bookRepository.findById(id);
        if (book.isPresent()) {
            logger.info("Cartea cu ID-ul {} a fost găsită: {}", id, book.get().getTitle());
        } else {
            logger.warn("Cartea cu ID-ul {} nu a fost găsită", id);
        }
        return book;
    }

    public Optional<Book> getBookByTitle(String title) { 
        logger.info("Căutare carte după titlu: {}", title);
        Optional<Book> book = bookRepository.findByTitle(title);
        if (book.isPresent()) {
            logger.info("Cartea cu titlul '{}' a fost găsită", title);
        } else {
            logger.warn("Cartea cu titlul '{}' nu a fost găsită", title);
        }
        return book;
    }

    public List<Book> getAllBooks() {
        logger.info("Obținerea tuturor cărților");
        List<Book> books = bookRepository.findAll();
        logger.info("S-au returnat {} cărți", books.size());
        return books;
    }

    public void deleteBook(Long id) {
        logger.info("Ștergere carte cu ID-ul: {}", id);
        bookRepository.deleteById(id);
        logger.info("Cartea cu ID-ul {} a fost ștearsă", id);
    }

    // pentru paginare:
    public Page<Book> getBooks(Pageable pageable) {
        logger.info("Obținere cărți paginate - pagina {}, dimensiune {}", pageable.getPageNumber(), pageable.getPageSize());
        Page<Book> bookPage = bookRepository.findAll(pageable);
        logger.info("S-au returnat {} cărți din {} disponibile", bookPage.getNumberOfElements(), bookPage.getTotalElements());
        return bookPage;
    }

}