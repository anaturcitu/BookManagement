package com.example.book_service.controller;

import com.example.book_service.dto.BookDTO;
import com.example.book_service.dto.GenreDTO;
import com.example.book_service.model.Book;
import com.example.book_service.service.BookService;
import com.example.book_service.FeignClient.GenreClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/books")
public class BookController {

    // ✅ Logger added for monitoring controller actions
    private static final Logger logger = LoggerFactory.getLogger(BookController.class);

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/home")
    public String home() {
        logger.info("GET /books/home - Welcome endpoint hit");
        return "Welcome to my Spring Boot app!";
    }

    // GET /books -> toate cărțile
    @GetMapping
    public List<Book> getAllBooks() {
        logger.info("GET /books - Fetching all books");
        return bookService.getAllBooks(); // metoda ar trebui să întoarcă List<Book>
    }

    // GET /books/{id} -> carte după ID
    @GetMapping("/{id}")
    public ResponseEntity<BookDTO> getBookById(@PathVariable Long id) {
        logger.info("GET /books/{} - Fetching book by ID", id);
        Book book = bookService.getBookById(id)
                .orElseThrow(() -> {
                    logger.warn("Book with ID {} not found", id);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found");
                });

        BookDTO dto = new BookDTO();
        dto.setId(book.getId());
        dto.setTitle(book.getTitle());

        logger.info("Book with ID {} found successfully", id);
        return ResponseEntity.ok(dto);
    }

    // POST /books -> adaugă o carte nouă
    @PostMapping
    public Book createBook(@RequestBody BookDTO bookDTO) {
        logger.info("POST /books - Creating new book: {}", bookDTO.getTitle());
        Book book = bookService.createBook(bookDTO); // metoda ar trebui să întoarcă Book
        logger.info("Book created successfully with ID: {}", book.getId());
        return book;
    }

    // DELETE /books/{id} -> șterge carte
    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable Long id) {
        logger.info("DELETE /books/{} - Deleting book", id);
        bookService.deleteBook(id);
        logger.info("Book with ID {} deleted", id);
    }

    // GET /books/search?title=xyz -> caută după titlu
    @GetMapping("/search")
    public Optional<Book> getBookByTitle(@RequestParam String title) {
        logger.info("GET /books/search?title={} - Searching book by title", title);
        return bookService.getBookByTitle(title);
    }

    @GetMapping("/{id}/genres")
    public List<GenreDTO> getGenresForBook(@PathVariable Long id) {
        logger.info("GET /books/{}/genres - Fetching genres for book", id);
        return bookService.getGenresForBook(id);
    }

}
