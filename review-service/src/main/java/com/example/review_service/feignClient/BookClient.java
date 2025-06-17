package com.example.review_service.feignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.http.ResponseEntity;

@FeignClient(
    name = "book-service"
)
public interface BookClient {

    @GetMapping("/books/{id}")
    ResponseEntity<?> getBookById(@PathVariable("id") Long id);

}
