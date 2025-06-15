package com.example.review_service.feignClient;

import com.example.review_service.dto.BookDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "book-service")
public interface BookClient {
    @GetMapping("/books/{id}")
    ResponseEntity<BookDTO> getBookById(@PathVariable("id") Long id);
}

