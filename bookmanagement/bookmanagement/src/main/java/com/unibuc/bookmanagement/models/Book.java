package com.unibuc.bookmanagement.models;
//import javax.persistence.GeneratedValue;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;

@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Title is required")
    private String title;

    private String description;

    @NotBlank(message = "ISBN is required")
    private String isbn;

    @NotNull(message = "Author ID is required")
    private Long authorId;

//    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
//    private List<UserBook> userBooks = new ArrayList<>();

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

    public void setId(Long id) { this.id = id; }
    public void setTitle(String title) { this.title = title; }
    public void setDescription(String description) { this.description = description; }
    public void setIsbn(String isbn) { this.isbn = isbn; }
    public void setAuthorId(Long authorId) { this.authorId = authorId; }
}
