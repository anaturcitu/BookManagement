package com.example.book_service.FeignClient;

import com.example.book_service.dto.AuthorDTO;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuthorClientFallback implements AuthorClient {

    @Override
    public Optional<AuthorDTO> getAuthorById(Long id) {
        AuthorDTO fallbackAuthor = new AuthorDTO();
        fallbackAuthor.setName("Autor indisponibil");
        return Optional.of(fallbackAuthor);
    }
}
