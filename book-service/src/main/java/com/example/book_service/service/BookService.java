package com.example.book_service.service;

import com.example.book_service.FeignClient.AuthorClient;
import com.example.book_service.FeignClient.GenreClient;
import com.example.book_service.dto.AuthorDTO;
import com.example.book_service.dto.BookDTO;
import com.example.book_service.dto.GenreDTO;
import com.example.book_service.model.Book;
import com.example.book_service.repository.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookService {

    private static final Logger logger = LoggerFactory.getLogger(BookService.class);

    private final BookRepository bookRepository;
    private final AuthorClient authorClient;
    private final GenreClient genreClient;

    public BookService(BookRepository bookRepository, AuthorClient authorClient, GenreClient genreClient) {
        this.bookRepository = bookRepository;
        this.authorClient = authorClient;
        this.genreClient = genreClient;
    }

    public Book createBook(BookDTO bookDTO) {
        logger.info("Creating book with title: {}", bookDTO.getTitle());

        // verificam daca autorul exista in author-service
        Optional<AuthorDTO> author = authorClient.getAuthorById(bookDTO.getAuthorId());
        if (author.isEmpty()) {
            logger.warn("Author with ID {} does not exist", bookDTO.getAuthorId());
            throw new RuntimeException("Author with ID " + bookDTO.getAuthorId() + " does not exist.");
        }

        Book book = new Book();
        book.setTitle(bookDTO.getTitle());
        book.setIsbn(bookDTO.getIsbn());
        book.setDescription(bookDTO.getDescription());
        book.setAuthorId(bookDTO.getAuthorId());
        book.setGenreIds(bookDTO.getGenreIds());

        Book saved = bookRepository.save(book);
        logger.info("Book saved with ID: {}", saved.getId());
        return saved;
    }

    public Optional<Book> getBookById(Long id) {
        logger.info("Fetching book with ID: {}", id);
        return bookRepository.findById(id);
    }

    public Optional<Book> getBookByTitle(String title) {
        logger.info("Searching for book with title: {}", title);
        return bookRepository.findByTitle(title);
    }

    public List<Book> getAllBooks() {
        logger.info("Fetching all books from repository");
        return bookRepository.findAll();
    }

    public void deleteBook(Long id) {
        logger.info("Deleting book with ID: {}", id);
        bookRepository.deleteById(id);
        logger.info("Book with ID {} deleted", id);
    }

    public List<GenreDTO> getGenresForBook(Long bookId) {
        logger.info("Fetching genres for book with ID: {}", bookId);
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> {
                    logger.warn("Book with ID {} not found", bookId);
                    return new RuntimeException("Book not found");
                });

        logger.info("Book found. Mapping genre IDs to GenreDTOs.");
        return book.getGenreIds().stream()
                .map(genreClient::getGenreById)
                .collect(Collectors.toList());
    }
}
