package com.unibuc.bookmanagement.junction_tables;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

// clasa care formeaza cheia primara compusa dintre Users si Books
@Embeddable
public class UserBookId implements Serializable {
    private Long userId;
    private Long bookId;

    public UserBookId() {}

    public UserBookId(Long userId, Long bookId) {
        this.userId = userId;
        this.bookId = bookId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserBookId that = (UserBookId) o;
        return Objects.equals(userId, that.userId) && Objects.equals(bookId, that.bookId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, bookId);
    }

    public Long getUserId() { return userId; }
    public Long getBookId() { return bookId; }

    public void setUserId(Long userId) { this.userId = userId; }
    public void setBookId(Long bookId) { this.bookId = bookId; }
}
