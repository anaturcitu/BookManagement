package com.unibuc.bookmanagement.controllers;

import com.unibuc.bookmanagement.dto.BookDTO;
import com.unibuc.bookmanagement.models.Author;
import com.unibuc.bookmanagement.models.Book;
import com.unibuc.bookmanagement.services.AuthorService;
import com.unibuc.bookmanagement.services.BookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    @GetMapping("/{id}")
    public String bookDetails(@PathVariable Long id, Model model) {
        Optional<Book> bookOpt = bookService.getBookById(id);
        if (bookOpt.isPresent()) {
            Book book = bookOpt.get();
            Author author = authorService.getAuthorById(book.getAuthorId()).orElse(null);

            BookDTO bookDTO = new BookDTO();
            bookDTO.setId(book.getId());
            bookDTO.setTitle(book.getTitle());
            bookDTO.setDescription(book.getDescription());
            bookDTO.setIsbn(book.getIsbn());
            if (author != null) {
                bookDTO.setAuthorName(author.getName());
            }

            model.addAttribute("book", bookDTO);
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

//    @GetMapping
//    public String getAllBooks(Model model) {
//        List<Book> books = bookService.getAllBooks();
//        model.addAttribute("books", books);
//        return "book_list"; // va cauta book_list.html in /resources/templates
//    }

    // pentru paginare:
    @GetMapping
    public String getAllBooks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "title") String sort,
            @RequestParam(defaultValue = "asc") String dir,
            Model model
    ) {
        Sort.Direction direction = dir.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sort));
        Page<Book> bookPage = bookService.getBooks(pageable);

        model.addAttribute("bookPage", bookPage);
        return "book_list";
    }

    @GetMapping("/add")
    public String showAddBookForm(Model model) {
        model.addAttribute("book", new BookDTO());
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
