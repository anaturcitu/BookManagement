package com.example.book_service.FeignClient;

import com.example.book_service.dto.GenreDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "genre-service", fallback = GenreClientFallback.class)
public interface GenreClient {
    @GetMapping("/genres/{id}")
    GenreDTO getGenreById(@PathVariable("id") Long id);
}
