package com.example.book_service.dto;

public class AuthorDTO {
    private Long id;
    private String name;
    private String birthDate;

    public AuthorDTO() {}

    public AuthorDTO(Long id, String name, String birthDate) {
        this.id = id;
        this.name = name;
        this.birthDate = birthDate;
    }

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getBirthDate() { return birthDate; }

    public void setBirthDate(String birthDate) { this.birthDate = birthDate; }

}

