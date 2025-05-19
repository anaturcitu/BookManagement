package com.unibuc.bookmanagement.services;

import com.unibuc.bookmanagement.dto.RegisterRequest;
import com.unibuc.bookmanagement.models.User;
import com.unibuc.bookmanagement.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class UserService {
    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean emailExists(String email) {
        log.info("Verificare dacă emailul există: {}", email);
        return userRepository.existsByEmail(email);
    }

    public void registerUser(RegisterRequest request) {
        log.info("Se înregistrează un utilizator cu username: {}", request.getUsername());

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole("user");
        userRepository.save(user);

        log.info("Utilizator înregistrat cu succes: {}", user.getUsername());
    }

    public User createUser(User user) {
        log.info("Se creează un nou utilizator: {}", user.getUsername());
        return userRepository.save(user);
    }

    public Optional<User> getUserById(Long id) {
        log.info("Se caută utilizatorul cu ID: {}", id);
        return userRepository.findById(id);
    }

    public User findByEmail(String email) {
        log.info("Se caută utilizatorul cu email: {}", email);
        return userRepository.findByEmail(email)
                .orElseThrow(() -> {
                    log.warn("Utilizatorul nu a fost găsit cu email: {}", email);
                    return new UsernameNotFoundException("Utilizatorul nu a fost găsit cu email: " + email);
                });
    }

    public void deleteUser(Long id) {
        log.info("Se șterge utilizatorul cu ID: {}", id);
        userRepository.deleteById(id);
        log.info("Utilizator șters cu succes");
    }

    public boolean usernameExists(String username) {
        log.info("Verificare dacă username-ul există: {}", username);
        return userRepository.existsByUsername(username);
    }

    public Optional<User> findByUsername(String username) {
        log.info("Se caută utilizatorul cu username: {}", username);
        return userRepository.findByUsername(username);
    }
}
