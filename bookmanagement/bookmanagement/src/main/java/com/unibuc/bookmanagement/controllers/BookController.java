package com.unibuc.bookmanagement.controllers;

import com.unibuc.bookmanagement.models.Author;
import com.unibuc.bookmanagement.models.Book;
import com.unibuc.bookmanagement.services.AuthorService;
import com.unibuc.bookmanagement.services.BookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/books")
public class BookController {
    @Autowired
    private BookService bookService;

    @Autowired
    private AuthorService authorService;


    @GetMapping("/{id}") // afiseaza detaliile cartii (ma duce la pagina html book-details)
    public String bookDetails(@PathVariable Long id, Model model) {
        Optional<Book> book = bookService.getBookById(id);
        if (book.isPresent()) {
            Book b = book.get();
            Author author = authorService.getAuthorById(b.getAuthorId()).orElse(null);
            model.addAttribute("book", b);
            model.addAttribute("author", author);
            return "book-details";
        } else {
            return "redirect:/books";
        }
    }

    @GetMapping("/find")
    public String getBookByTitle(@RequestParam("query") String title, RedirectAttributes redirectAttributes) {
        Optional<Book> book = bookService.getBookByTitle(title);
        if (book.isPresent()) {
            return "redirect:/books/" + book.get().getId();
        } else {
            redirectAttributes.addFlashAttribute("error", "Cartea nu a fost găsită.");
            return "redirect:/books";
        }
    }

    @GetMapping
    public String getAllBooks(Model model) {
        List<Book> books = bookService.getAllBooks();
        model.addAttribute("books", books);
        return "book_list"; // va cauta book_list.html in /resources/templates
    }

    @GetMapping("/add")
    public String showAddBookForm(Model model) {
        model.addAttribute("book", new Book());
        return "add_book";
    }

    @PostMapping("/add")
    public String addBook(@Valid @ModelAttribute("book") Book book, BindingResult result) {
        if (result.hasErrors()) {
            return "add_book";
        }
        bookService.createBook(book);
        return "redirect:/books";
    }


    @PostMapping("/delete/{id}")
    public String deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return "redirect:/books";
    }
}
