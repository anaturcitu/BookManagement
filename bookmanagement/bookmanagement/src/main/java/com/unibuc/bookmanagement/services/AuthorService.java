package com.unibuc.bookmanagement.services;

import com.unibuc.bookmanagement.dto.AuthorDTO;
import com.unibuc.bookmanagement.models.Author;
import com.unibuc.bookmanagement.repositories.AuthorRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class AuthorService {

    private static final Logger logger = LoggerFactory.getLogger(AuthorService.class);

    @Autowired
    private AuthorRepository authorRepository;

    // creeaza un autor nou
    public Author createAuthor(AuthorDTO dto) {
        Author a = new Author();
        a.setName(dto.getName());

        if (dto.getBirthDate() != null && !dto.getBirthDate().isBlank()) {
            try {
                a.setBirthDate(LocalDate.parse(dto.getBirthDate())); // <- conversie din String
            } catch (Exception e) {
                logger.warn("Nu s-a putut converti birthDate: {}", dto.getBirthDate(), e);
            }
        }

        return authorRepository.save(a);
    }


    // gaseste un autor dupa id
    public Optional<Author> getAuthorById(Long id) {
        logger.info("Se cauta autorul cu ID-ul: {}", id);
        return authorRepository.findById(id);
    }

    // gaseste un autor dupa nume
    public Optional<Author> getAuthorByName(String name) {
        logger.info("Se cauta autorul cu numele: {}", name);
        return authorRepository.findByName(name);
    }

    // obtine toti autorii => POSTMAN
    public List<Author> getAllAuthors() {
        logger.info("Se obtin toti autorii din baza");
        return authorRepository.findAll();
    }

    // obtine autorii paginat => HTML
    public Page<Author> getAllAuthorsPaged(Pageable pageable) {
        logger.info("Se obtin autorii in mod paginat");
        return authorRepository.findAll(pageable);
    }

    // sterge un autor dupa id
    public void deleteAuthor(Long id) {
        logger.info("Se sterge autorul cu ID-ul: {}", id);
        authorRepository.deleteById(id);
    }
}
