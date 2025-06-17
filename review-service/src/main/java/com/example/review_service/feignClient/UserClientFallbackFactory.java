package com.example.review_service.feignClient;

import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class UserClientFallbackFactory implements FallbackFactory<UserClient> {
    @Override
    public UserClient create(Throwable cause) {
        return new UserClient() {
            @Override
            public ResponseEntity<?> getUserById(Long id) {
                System.out.println(">>> USER FALLBACK ACTIVATED: " + cause.getMessage());
                return ResponseEntity
                        .status(HttpStatus.SERVICE_UNAVAILABLE)
                        .body("User Service is currently unavailable");
            }
        };
    }
}
