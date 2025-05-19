package com.unibuc.bookmanagement.services;

import com.unibuc.bookmanagement.models.Author;
import com.unibuc.bookmanagement.repositories.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorService {

    private static final Logger logger = LoggerFactory.getLogger(AuthorService.class);

    @Autowired
    private AuthorRepository authorRepository;

    // creeaza un autor nou
    public Author createAuthor(Author author) {
        logger.info("Se creează un autor nou: {}", author.getName());
        return authorRepository.save(author);
    }

    // gaseste un autor dupa id
    public Optional<Author> getAuthorById(Long id) {
        logger.info("Se caută autorul cu ID-ul: {}", id);
        return authorRepository.findById(id);
    }

    // gaseste un autor dupa nume
    public Optional<Author> getAuthorByName(String name) {
        logger.info("Se caută autorul cu numele: {}", name);
        return authorRepository.findByName(name);
    }

    // obtine toti autorii
    public List<Author> getAllAuthors() {
        logger.info("Se obțin toți autorii din bază");
        return authorRepository.findAll();
    }

    // sterge un autor dupa id
    public void deleteAuthor(Long id) {
        logger.info("Se șterge autorul cu ID-ul: {}", id);
        authorRepository.deleteById(id);
    }
}
