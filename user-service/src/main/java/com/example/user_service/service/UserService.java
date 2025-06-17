package com.example.user_service.service;

import com.example.user_service.dto.RegisterRequest;
import com.example.user_service.model.User;
import com.example.user_service.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.List;

@Service
public class UserService {

    // ✅ Logger pentru monitorizarea operațiunilor
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> getAllUsers() {
        logger.info("Se obține lista tuturor utilizatorilor");
        return userRepository.findAll();
    }

    public boolean emailExists(String email) {
        boolean exists = userRepository.existsByEmail(email);
        logger.info("Verificare existență email {}: {}", email, exists);
        return exists;
    }

    public boolean usernameExists(String username) {
        boolean exists = userRepository.existsByUsername(username);
        logger.info("Verificare existență username {}: {}", username, exists);
        return exists;
    }

    public void registerUser(RegisterRequest request) {
        logger.info("Înregistrare utilizator: {}", request.getEmail());
        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole("user");

        userRepository.save(user);
        logger.info("Utilizator salvat cu succes: {}", request.getEmail());
    }

    public User createUser(User user) {
        logger.info("Creare utilizator: {}", user.getEmail());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User saved = userRepository.save(user);
        logger.info("Utilizator creat cu ID: {}", saved.getId());
        return saved;
    }

    public Optional<User> getUserById(Long id) {
        logger.info("Se caută utilizatorul cu ID-ul: {}", id);
        return userRepository.findById(id);
    }

    public Optional<User> findByUsername(String username) {
        logger.info("Se caută utilizatorul cu username-ul: {}", username);
        return userRepository.findByUsername(username);
    }

    public User findByEmail(String email) {
        logger.info("Se caută utilizatorul cu email-ul: {}", email);
        return userRepository.findByEmail(email)
                .orElseThrow(() -> {
                    logger.warn("Utilizatorul cu emailul {} nu a fost găsit", email);
                    return new UsernameNotFoundException("Utilizatorul nu a fost găsit cu email: " + email);
                });
    }

    public void deleteUser(Long id) {
        logger.info("Se șterge utilizatorul cu ID-ul: {}", id);
        userRepository.deleteById(id);
        logger.info("Utilizatorul cu ID-ul {} a fost șters", id);
    }
}
