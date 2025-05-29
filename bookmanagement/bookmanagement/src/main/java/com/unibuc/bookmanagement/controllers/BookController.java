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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

@Controller
@RequestMapping("/books")
public class BookController {

    private static final Logger logger = LoggerFactory.getLogger(BookController.class);

    @Autowired
    private BookService bookService;

    @Autowired
    private AuthorService authorService;

    @GetMapping("/{id}")
    public String bookDetails(@PathVariable Long id, Model model) {
        logger.info("Se accesează detalii pentru cartea cu ID-ul: {}", id);
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
            logger.warn("Cartea cu ID-ul {} nu a fost găsită", id);
            return "redirect:/books";
        }
    }

    @GetMapping("/find")
    public String getBookByTitle(@RequestParam("query") String title, RedirectAttributes redirectAttributes) {
        logger.info("Se caută cartea cu titlul: {}", title);
        Optional<Book> book = bookService.getBookByTitle(title);
        if (book.isPresent()) {
            return "redirect:/books/" + book.get().getId();
        } else {
            logger.warn("Cartea cu titlul \"{}\" nu a fost găsită", title);
            redirectAttributes.addFlashAttribute("error", "Cartea nu a fost găsită.");
            return "redirect:/books";
        }
    }

    // pentru paginare:
    @GetMapping
    public String getAllBooks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "title") String sort,
            @RequestParam(defaultValue = "asc") String dir,
            Model model
    ) {
        logger.info("Se obține lista de cărți - pagină: {}, dimensiune: {}, sortare după: {}, direcție: {}",
                page, size, sort, dir);
        Sort.Direction direction = dir.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sort));
        Page<Book> bookPage = bookService.getBooks(pageable);

        model.addAttribute("bookPage", bookPage);
        return "book_list";
    }

    @GetMapping("/add")
    public String showAddBookForm(Model model) {
        logger.info("Se afișează formularul de adăugare a unei cărți");
        model.addAttribute("book", new BookDTO());
        model.addAttribute("authors", authorService.getAllAuthors()); // <- trimitem lista autorilor
        return "add_book";
    }

    @PostMapping("/add")
    public String addBook(@Valid @ModelAttribute("book") BookDTO bookDTO, BindingResult result) {
        if (result.hasErrors()) {
            logger.warn("Eroare la validarea formularului de adăugare carte: {}", result.getAllErrors());
            return "add_book";
        }
        logger.info("Se adaugă o carte nouă: {}", bookDTO.getTitle());
        bookService.createBook(bookDTO);
        return "redirect:/books";
    }

    @PostMapping("/delete/{id}")
    public String deleteBook(@PathVariable Long id) {
        logger.info("Se șterge cartea cu ID-ul: {}", id);
        bookService.deleteBook(id);
        return "redirect:/books";
    }
}
