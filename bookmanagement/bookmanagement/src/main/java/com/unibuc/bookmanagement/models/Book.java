package com.unibuc.bookmanagement.models;
//import javax.persistence.GeneratedValue;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Title is required")
    @Size(max = 50, message = "Title must not exceed 50 characters")
    private String title;

    @Size(max = 500, message = "Description must not exceed 500 characters")
    private String description;

    @NotBlank(message = "ISBN is required")
    @Pattern(regexp = "\\d{13}", message = "ISBN must be a 13-digit number")
    private String isbn;

    @NotNull(message = "Author ID is required")
    private Long authorId;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserBook> userBooks = new ArrayList<>();

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews = new ArrayList<>();

    @ManyToMany(cascade = {CascadeType.MERGE})
    @JoinTable(
            name = "bookgenres", // numele tabelei intermediare
            joinColumns = @JoinColumn(name = "book_id"), // coloana cheie straina din tabela intermediara
            inverseJoinColumns = @JoinColumn(name = "genre_id") // coloana cheie straina din tabela intermediara
    )
    @JsonIgnoreProperties("books")
    private Set<Genre> genres;

    public Book(){}

    public Book(Long id, String title, String description, String isbn, Long authorId) // plus id si id_author
    {
        this.id = id;
        this.title = title;
        this.description = description;
        this.isbn = isbn;
        this.authorId = authorId;
    }

    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getIsbn() { return isbn; }
    public Long getAuthorId() { return authorId; }
    public Set<Genre> getGenres() { return genres; }

    public void setId(Long id) { this.id = id; }
    public void setTitle(String title) { this.title = title; }
    public void setDescription(String description) { this.description = description; }
    public void setIsbn(String isbn) { this.isbn = isbn; }
    public void setAuthorId(Long authorId) { this.authorId = authorId; }
    public void setGenres(Set<Genre> genres) { this.genres = genres; }
}
