package com.example.review_service.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

@Entity
@Table(name = "reviews")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 1500, message = "Content must not exceed 1500 characters")
    @Column
    private String content;

    @NotNull(message = "Rating is required")
    @Min(value = 1, message = "Rating must be at least 1")
    @Max(value = 5, message = "Rating must be at most 5")
    @Column(nullable = false)
    private Integer rating;

    private LocalDateTime createdAt;

    @NotNull(message = "User ID is required")
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @NotNull(message = "Book ID is required")
    @Column(name = "book_id", nullable = false)
    private Long bookId;


    @PrePersist
    protected void onCreate() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();  // setam data curenta
        }
    }

    public Long getId() { return id; }
    public String getContent() { return content; }
    public Integer getRating() { return rating; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public Long getUserId() { return userId; }
    public Long getBookId() { return bookId; }

    public void setId(Long id) { this.id = id; }
    public void setContent(String content) { this.content = content; }
    public void setRating(Integer rating) { this.rating = rating; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public void setUserId(Long userId) { this.userId = userId; }
    public void setBookId(Long bookId) { this.bookId = bookId; }
}

