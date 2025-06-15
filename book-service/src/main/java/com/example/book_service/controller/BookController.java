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

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/home")
    public String home() {
        return "Welcome to my Spring Boot app!";
    }

    // GET /books -> toate cărțile
    @GetMapping
    public List<Book> getAllBooks() {
        return bookService.getAllBooks(); // metoda ar trebui să întoarcă List<Book>
    }

    // GET /books/{id} -> carte după ID
    @GetMapping("/{id}")
    public ResponseEntity<BookDTO> getBookById(@PathVariable Long id) {
        Book book = bookService.getBookById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found"));

        BookDTO dto = new BookDTO();
        dto.setId(book.getId());
        dto.setTitle(book.getTitle());

        return ResponseEntity.ok(dto);
    }


    // POST /books -> adaugă o carte nouă
    @PostMapping
    public Book createBook(@RequestBody BookDTO bookDTO) {
        return bookService.createBook(bookDTO); // metoda ar trebui să întoarcă Book
    }

    // DELETE /books/{id} -> șterge carte
    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
    }

    // GET /books/search?title=xyz -> caută după titlu
    @GetMapping("/search")
    public Optional<Book> getBookByTitle(@RequestParam String title) {
        return bookService.getBookByTitle(title);
    }

    @GetMapping("/{id}/genres")
    public List<GenreDTO> getGenresForBook(@PathVariable Long id) {
        return bookService.getGenresForBook(id);
    }

}
