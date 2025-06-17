package com.example.book_service.FeignClient;

import com.example.book_service.dto.AuthorDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@FeignClient(name = "author-service", fallback = AuthorClientFallback.class)
public interface AuthorClient {

    @GetMapping("/authors/{id}")
    Optional<AuthorDTO> getAuthorById(@PathVariable("id") Long id);
}

