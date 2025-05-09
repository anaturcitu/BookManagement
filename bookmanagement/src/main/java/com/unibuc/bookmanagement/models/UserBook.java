package com.unibuc.bookmanagement.models;

import com.unibuc.bookmanagement.junction_tables.UserBookId;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

@Entity
@Table(name = "userbooks")
public class UserBook {
    @EmbeddedId
    private UserBookId id;
    private String status;

    private LocalDateTime addedAt;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @MapsId("bookId")
    @JoinColumn(name = "book_id")
    private Book book;

    public UserBook(){}

    public UserBook(UserBookId id, String status, LocalDateTime addedAt, User user, Book book) {
        this.id = id;
        this.status = status;
        this.addedAt = addedAt;
        this.user = user;
        this.book = book;
    }

    @PrePersist
    protected void onCreate() {
        if (addedAt == null) {
            addedAt = LocalDateTime.now();  // setam data curenta
        }
    }

    public UserBookId getId() { return id; }
    public String getStatus() { return status; }
    public LocalDateTime getAddedAt() { return addedAt; }
    public User getUser() { return user; }
    public Book getBook() { return book; }

    public void setId(UserBookId id) { this.id = id; }
    public void setStatus(String status) { this.status = status; }
    public void setAddedAt(LocalDateTime addedAt) { this.addedAt = addedAt; }
    public void setUser(User user) { this.user = user; }
    public void setBook(Book book) { this.book = book; }
}
