package com.unibuc.bookmanagement.controllers;

import com.unibuc.bookmanagement.dto.AuthorDTO;
import com.unibuc.bookmanagement.services.AuthorService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/authors")
public class AuthorController {

    private static final Logger logger = LoggerFactory.getLogger(AuthorController.class);

    private final AuthorService authorService;
       
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    // formular HTML pentru adaugare autor
    // @GetMapping("/add")
    // public String showAddAuthorForm(Model model) {
    //     logger.info("Se acceseaza formularul de adaugare autor");
    //     model.addAttribute("authorDTO", new AuthorDTO());
    //     return "authors/add-author";
    // }

    @GetMapping("/add")
    public String showAddAuthorForm(Model model) {
        try {
            model.addAttribute("authorDTO", new AuthorDTO());
            return "authors/add-author";
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }


    // proceseaza formularul HTML
    @PostMapping("/add")
    public String addAuthor(@ModelAttribute("authorDTO") @Valid AuthorDTO dto,
                            BindingResult result) {

        if (result.hasErrors()) {
            logger.warn("Erori de validare la salvarea autorului: {}", result.getAllErrors());
            return "authors/add-author";
        }

        authorService.createAuthor(dto);
        logger.info("Autor salvat cu succes: {}", dto.getName());
        return "redirect:/authors?success";
    }

    // lista autorilor cu paginare si sortare
    @GetMapping
    public String listAuthors(Model model,
                              @RequestParam(defaultValue = "0") int page,
                              @RequestParam(defaultValue = "5") int size,
                              @RequestParam(defaultValue = "name") String sort,
                              @RequestParam(defaultValue = "asc") String dir) {

        logger.info("Se afiseaza lista autorilor: page={}, size={}, sort={}, dir={}", page, size, sort, dir);

        Sort.Direction direction = dir.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sort));

        model.addAttribute("authors", authorService.getAllAuthorsPaged(pageable));
        model.addAttribute("currentPage", page);
        model.addAttribute("size", size);
        model.addAttribute("sort", sort);
        model.addAttribute("dir", dir);

        return "authors/list";
    }
}
