package com.unibuc.bookmanagement.models;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "book_info")
public class BookInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "book_id", referencedColumnName = "id", unique = true)
    private Book book;

    @Column(name = "page_count")
    private Integer pageCount;

    @Column(name = "publication_date")
    private LocalDate publicationDate;

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public Book getBook() { return book; }

    public void setBook(Book book) { this.book = book; }

    public Integer getPageCount() { return pageCount; }

    public void setPageCount(Integer pageCount) { this.pageCount = pageCount; }

    public LocalDate getPublicationDate() { return publicationDate; }

    public void setPublicationDate(LocalDate publicationDate) { this.publicationDate = publicationDate; }

}
