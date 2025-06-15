package com.example.author_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class AuthorDTO {

    private Long id;

    @NotBlank(message = "Numele este obligatoriu")
    @Size(max = 50, message = "Numele nu trebuie să depășească 50 de caractere")
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
