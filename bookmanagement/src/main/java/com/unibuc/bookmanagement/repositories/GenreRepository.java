package com.unibuc.bookmanagement.repositories;

import com.unibuc.bookmanagement.models.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRepository extends JpaRepository<Genre, Long> {
}
