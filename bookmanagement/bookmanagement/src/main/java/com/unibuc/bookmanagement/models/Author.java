package com.unibuc.bookmanagement.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "authors")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name is required")
    private String name;

    private String birth_date;

    public Author(){}
    public Author(Long id, String name, String birth_date)
    {
        this.id = id;
        this.name = name;
        this.birth_date = birth_date;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getBirth_date() { return birth_date; }

    public void setId(Long id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setBirth_date(String birth_date) { this.birth_date = birth_date; }
}