package com.example.review_service.feignClient;

import com.example.review_service.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service")
public interface UserClient {
    @GetMapping("/users/{id}")
    ResponseEntity<UserDTO> getUserById(@PathVariable("id") Long id);
}

