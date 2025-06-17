package com.example.user_service.controller;

import com.example.user_service.dto.RegisterRequest;
import com.example.user_service.model.User;
import com.example.user_service.service.UserService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    // POST /users/register -> înregistrare utilizator
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody @Valid RegisterRequest request) {
        logger.info("POST /users/register - Se încearcă înregistrarea utilizatorului cu email: {}", request.getEmail());

        if (!request.getPassword().equals(request.getConfirmPassword())) {
            logger.warn("Parolele nu se potrivesc pentru email: {}", request.getEmail());
            return ResponseEntity
                    .badRequest()
                    .body("Parolele nu se potrivesc");
        }

        if (userService.emailExists(request.getEmail())) {
            logger.warn("Email deja utilizat: {}", request.getEmail());
            return ResponseEntity
                    .badRequest()
                    .body("Email deja utilizat");
        }

        userService.registerUser(request);
        logger.info("Utilizator înregistrat cu succes: {}", request.getEmail());
        return ResponseEntity
                .ok("Utilizator înregistrat cu succes");
    }

    // GET /users/login -> (doar mesaj demo, nu login real)
    @GetMapping("/login")
    public String loginInfo() {
        logger.info("GET /users/login - endpoint de informare accesat");
        return "Pentru login, folosește autentificare JWT sau Basic Auth (nu e implementat aici)";
    }

    // GET /users -> lista tuturor utilizatorilor
    @GetMapping
    public List<User> getAllUsers() {
        logger.info("GET /users - Se obține lista tuturor utilizatorilor");
        return userService.getAllUsers();
    }

    // GET /users/{id}
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        logger.info("GET /users/{} - Se caută utilizatorul după ID", id);
        return userService.getUserById(id)
                .map(user -> {
                    logger.info("Utilizator găsit: {}", user.getEmail());
                    return ResponseEntity.ok(user);
                })
                .orElseGet(() -> {
                    logger.warn("Utilizator cu ID-ul {} nu a fost găsit", id);
                    return ResponseEntity.notFound().build();
                });
    }

    // GET /users/email/{email}
    @GetMapping("/email/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable String email) {
        logger.info("GET /users/email/{} - Se caută utilizatorul după email", email);
        try {
            User user = userService.findByEmail(email);
            logger.info("Utilizator găsit: {}", user.getEmail());
            return ResponseEntity.ok(user);
        } catch (UsernameNotFoundException e) {
            logger.warn("Utilizator cu emailul {} nu a fost găsit", email);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // DELETE /users/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        logger.info("DELETE /users/{} - Se șterge utilizatorul", id);
        userService.deleteUser(id);
        logger.info("Utilizator cu ID-ul {} a fost șters", id);
        return ResponseEntity.noContent().build();
    }

}
