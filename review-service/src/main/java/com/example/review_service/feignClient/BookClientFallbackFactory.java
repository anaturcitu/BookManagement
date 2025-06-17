package com.example.review_service.feignClient;

import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class BookClientFallbackFactory implements FallbackFactory<BookClient> {

    @Override
    public BookClient create(Throwable cause) {
        return new BookClient() {
            @Override
            public ResponseEntity<?> getBookById(Long id) {
                System.out.println(">>> BOOK FALLBACK ACTIVATED: " + cause.getMessage());
                return ResponseEntity
                        .status(HttpStatus.SERVICE_UNAVAILABLE)
                        .body("Book Service is currently unavailable");
            }
        };
    }
}
