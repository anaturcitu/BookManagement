package com.example.book_service.FeignClient;

import com.example.book_service.dto.GenreDTO;
import org.springframework.stereotype.Component;

@Component
public class GenreClientFallback implements GenreClient {

    @Override
    public GenreDTO getGenreById(Long id) {
        GenreDTO fallbackGenre = new GenreDTO();
        fallbackGenre.setName("Gen indisponibil");
        return fallbackGenre;
    }
}
