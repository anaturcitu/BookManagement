package com.example.book_service.service;

import com.example.book_service.FeignClient.AuthorClient;
import com.example.book_service.FeignClient.GenreClient;
import com.example.book_service.dto.AuthorDTO;
import com.example.book_service.dto.BookDTO;
import com.example.book_service.dto.GenreDTO;
import com.example.book_service.model.Book;
import com.example.book_service.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final AuthorClient authorClient;
    private final GenreClient genreClient;

    public BookService(BookRepository bookRepository, AuthorClient authorClient, GenreClient genreClient) {
        this.bookRepository = bookRepository;
        this.authorClient = authorClient;
        this.genreClient = genreClient;
    }

    public Book createBook(BookDTO bookDTO) {
        // verificam daca autorul exista in author-service
        Optional<AuthorDTO> author = authorClient.getAuthorById(bookDTO.getAuthorId());
        if (author.isEmpty()) {
            throw new RuntimeException("Author with ID " + bookDTO.getAuthorId() + " does not exist.");
        }

        Book book = new Book();
        book.setTitle(bookDTO.getTitle());
        book.setIsbn(bookDTO.getIsbn());
        book.setDescription(bookDTO.getDescription());
        book.setAuthorId(bookDTO.getAuthorId());
        book.setGenreIds(bookDTO.getGenreIds());

        return bookRepository.save(book);
    }

    public Optional<Book> getBookById(Long id) {
        return bookRepository.findById(id);
    }

    public Optional<Book> getBookByTitle(String title) {
        return bookRepository.findByTitle(title);
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

    public List<GenreDTO> getGenresForBook(Long bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        return book.getGenreIds().stream()
                .map(genreClient::getGenreById)
                .collect(Collectors.toList());
    }

}

