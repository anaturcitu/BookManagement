package com.unibuc.bookmanagement.services;

import com.unibuc.bookmanagement.dto.RegisterRequest;
import com.unibuc.bookmanagement.models.User;
import com.unibuc.bookmanagement.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@Slf4j
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean emailExists(String email) {
        boolean exists = userRepository.existsByEmail(email);
        log.info("Verificare existență email '{}': {}", email, exists);
        return exists;
    }

    public void registerUser(RegisterRequest request) {
        log.info("Înregistrare utilizator nou: {}", request.getEmail());
        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole("user");
        userRepository.save(user);
        log.info("Utilizator salvat cu succes: {}", user.getEmail());
    }

    public User createUser(User user) {
        log.info("Creare utilizator manual: {}", user.getEmail());
        return userRepository.save(user);
    }

    public Optional<User> getUserById(Long id) {
        log.info("Căutare utilizator după ID: {}", id);
        return userRepository.findById(id);
    }

    public User findByEmail(String email) {
        log.info("Căutare utilizator după email: {}", email);
        return userRepository.findByEmail(email)
                .orElseThrow(() -> {
                    log.warn("Utilizator cu emailul {} nu a fost găsit", email);
                    return new UsernameNotFoundException("User not found with email: " + email);
                });
    }

    public void deleteUser(Long id) {
        log.info("Ștergere utilizator cu ID: {}", id);
        userRepository.deleteById(id);
    }

    public boolean usernameExists(String username) {
        boolean exists = userRepository.existsByUsername(username);
        log.info("Verificare existență username '{}': {}", username, exists);
        return exists;
    }
}
