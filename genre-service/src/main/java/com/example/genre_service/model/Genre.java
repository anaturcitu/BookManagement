package com.example.genre_service.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Set;

@Entity
@Table(name = "genres")
public class Genre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name is required")
    @Size(max = 25, message = "Name must not exceed 25 characters")
    private String name;

//    @ManyToMany(mappedBy = "genres") // relatia inversa
//    @JsonIgnoreProperties("genres")
//    private Set<Book> books;

    public Genre() {}

    public Long getId() { return id; }
    public String getName() { return name; }
//    public Set<Book> getBooks() { return books; }

    public void setId(Long id) { this.id = id; }
    public void setName(String name) { this.name = name; }
//    public void setBooks(Set<Book> books) { this.books = books; }
}

