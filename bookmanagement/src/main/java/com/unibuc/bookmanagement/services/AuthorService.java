package com.unibuc.bookmanagement.services;

import com.unibuc.bookmanagement.models.Author;
import com.unibuc.bookmanagement.repositories.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    // creeaza un autor nou
    public Author createAuthor(Author author) {
        log.info("Se salvează autorul: {}", author.getName());
        return authorRepository.save(author);
    }

    // gaseste un autor dupa id
    public Optional<Author> getAuthorById(Long id) {
        log.info("Se caută autorul cu ID: {}", id);
        return authorRepository.findById(id);
    }

    // gaseste un autor dupa nume
    public Optional<Author> getAuthorByName(String name) {
        log.info("Se caută autorul cu numele: {}", name);
        return authorRepository.findByName(name);
    }

    // obtine toti autorii
    public List<Author> getAllAuthors() {
        log.info("Se obține lista completă de autori.");
        return authorRepository.findAll();
    }

    // sterge un autor dupa id
    public void deleteAuthor(Long id) {
        log.info("Se șterge autorul cu ID: {}", id);
        authorRepository.deleteById(id);
    }
}
