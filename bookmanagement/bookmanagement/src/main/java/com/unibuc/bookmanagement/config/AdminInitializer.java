package com.unibuc.bookmanagement.config;

import com.unibuc.bookmanagement.models.User;
import com.unibuc.bookmanagement.repositories.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Component
public class AdminInitializer {

    private static final Logger logger = LoggerFactory.getLogger(AdminInitializer.class);
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void init() {
        // verifica daca utilizatorul admin exista deja:
        if (!userRepository.existsByUsername("admin")) {
            logger.info("Admin user not found. Creating admin account...");
            // creaza utilizatorul admin:
            User admin = new User();
            admin.setUsername("admin");
            admin.setEmail("admin@example.com");
            admin.setPassword(passwordEncoder.encode("Parola123**")); // seteaza parola criptata
            admin.setRole("admin");

            // salveaza utilizatorul in baza de date:
            userRepository.save(admin);
            logger.info("Admin user created successfully with username: admin");
            System.out.println("Admin created!");
        } else {
            logger.info("Admin user already exists. Skipping creation.");
        }
    }
}