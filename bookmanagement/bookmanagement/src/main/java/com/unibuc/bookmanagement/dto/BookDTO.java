package com.unibuc.bookmanagement.dto;

import com.unibuc.bookmanagement.models.Genre;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.Set;

public class BookDTO {
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

    private String authorName;

    private Set<String> genreNames;

    public BookDTO() {}

    public BookDTO(Long id, String title, String description, String isbn, Long authorId, String authorName, Set<String> genreNames) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.isbn = isbn;
        this.authorId = authorId;
        this.authorName = authorName;
        this.genreNames = genreNames;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }
    public Long getAuthorId() { return authorId; }
    public void setAuthorId(Long authorId) { this.authorId = authorId; }
    public String getAuthorName() { return authorName; }
    public void setAuthorName(String authorName) { this.authorName = authorName; }
    public Set<String> getGenreNames() { return genreNames; }
    public void setGenreNames(Set<String> genreNames) { this.genreNames = genreNames; }
}
