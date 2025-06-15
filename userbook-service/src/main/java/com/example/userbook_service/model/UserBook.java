package com.example.userbook_service.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "userbooks")
public class UserBook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private Long bookId;

    private String status;
    private LocalDateTime addedAt;

    public UserBook() {}

    public UserBook(Long userId, Long bookId, String status, LocalDateTime addedAt) {
        this.userId = userId;
        this.bookId = bookId;
        this.status = status;
        this.addedAt = addedAt;
    }

    @PrePersist
    protected void onCreate() {
        if (addedAt == null) {
            addedAt = LocalDateTime.now();
        }
    }

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getBookId() {
        return bookId;
    }

    public String getStatus() {
        return status;
    }

    public LocalDateTime getAddedAt() {
        return addedAt;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setAddedAt(LocalDateTime addedAt) {
        this.addedAt = addedAt;
    }
}
