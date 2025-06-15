package com.example.book_service.dto;

public class GenreDTO {
    private Long id;
    private String name;

    public GenreDTO() {}

    public Long getId() { return id; }
    public String getName() { return name; }

    public void setId(Long id) { this.id = id; }
    public void setName(String name) { this.name = name; }
}
