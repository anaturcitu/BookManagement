package com.example.author_service.service;

import com.example.author_service.dto.AuthorDTO;
import com.example.author_service.model.Author;
import com.example.author_service.repository.AuthorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorService {

    private static final Logger logger = LoggerFactory.getLogger(AuthorService.class);

    @Autowired
    private AuthorRepository authorRepository;

    public Author createAuthor(AuthorDTO dto) {
        Author a = new Author();
        a.setName(dto.getName());

        if (dto.getBirthDate() != null && !dto.getBirthDate().isBlank()) {
            a.setBirthDate(dto.getBirthDate()); // fără conversie
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

    // sterge un autor dupa id
    public void deleteAuthor(Long id) {
        logger.info("Se sterge autorul cu ID-ul: {}", id);
        authorRepository.deleteById(id);
    }
}