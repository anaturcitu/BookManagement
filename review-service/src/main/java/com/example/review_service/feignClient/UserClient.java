package com.example.review_service.feignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/** Apel către user-service înregistrat în Eureka */
@FeignClient(name = "user-service")
public interface UserClient {
    @GetMapping("/users/{id}")
    ResponseEntity<?> getUserById(@PathVariable Long id);
}
