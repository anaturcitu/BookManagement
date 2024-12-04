package com.unibuc.bookmanagement.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "genres")
public class Genre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToMany(mappedBy = "genres") // relatia inversa
    @JsonIgnoreProperties("genres")
    private Set<Book> books;

    public Genre() {}

    public Long getId() { return id; }
    public String getName() { return name; }
    public Set<Book> getBooks() { return books; }

    public void setId(Long id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setBooks(Set<Book> books) { this.books = books; }
}
