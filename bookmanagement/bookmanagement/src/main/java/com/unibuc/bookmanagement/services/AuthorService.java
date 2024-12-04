package com.unibuc.bookmanagement.services;

import com.unibuc.bookmanagement.models.Author;
import com.unibuc.bookmanagement.repositories.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorService {
    @Autowired
    private AuthorRepository authorRepository;

    // creeaza un autor nou
    public Author createAuthor(Author author) {
        return authorRepository.save(author);
    }

    // gaseste un autor dupa id
    public Optional<Author> getAuthorById(Long id) {
        return authorRepository.findById(id);
    }

    // gaseste un autor dupa nume
    public Optional<Author> getAuthorByName(String name) {
        return authorRepository.findByName(name);
    }

    // obtine toti autorii
    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    // sterge un autor dupa id
    public void deleteAuthor(Long id) {
        authorRepository.deleteById(id);
    }
}
