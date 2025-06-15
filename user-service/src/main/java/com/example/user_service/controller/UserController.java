package com.example.user_service.controller;

import com.example.user_service.dto.RegisterRequest;
import com.example.user_service.model.User;
import com.example.user_service.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    // POST /users/register -> înregistrare utilizator
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody @Valid RegisterRequest request) {

        if (!request.getPassword().equals(request.getConfirmPassword())) {
            return ResponseEntity
                    .badRequest()
                    .body("Parolele nu se potrivesc");
        }

        if (userService.emailExists(request.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body("Email deja utilizat");
        }

        userService.registerUser(request);
        return ResponseEntity
                .ok("Utilizator înregistrat cu succes");
    }

    // GET /users/login -> (doar mesaj demo, nu login real)
    @GetMapping("/login")
    public String loginInfo() {
        return "Pentru login, folosește autentificare JWT sau Basic Auth (nu e implementat aici)";
    }

    // GET /users -> lista tuturor utilizatorilor
    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }


    // GET /users/{id}
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return userService.getUserById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // GET /users/email/{email}
    @GetMapping("/email/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable String email) {
        try {
            User user = userService.findByEmail(email);
            return ResponseEntity.ok(user);
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // DELETE /users/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

}

