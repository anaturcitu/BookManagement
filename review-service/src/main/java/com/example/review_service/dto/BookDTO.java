package com.example.review_service.dto;

public class BookDTO {
    private Long id;
    private String title;
    
    public BookDTO() {
    }


    public BookDTO(Long id, String title) {
        this.id = id;
        this.title = title;
    }

    public Long getId() {
        return id;
    }
    public String getTitle() {
        return title;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

