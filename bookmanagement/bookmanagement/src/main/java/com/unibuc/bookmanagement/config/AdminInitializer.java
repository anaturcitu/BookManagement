package com.unibuc.bookmanagement.config;

import com.unibuc.bookmanagement.models.User;
import com.unibuc.bookmanagement.repositories.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


@Component
@Profile("!test")
public class AdminInitializer {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AdminInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void init() {
        // verifica daca utilizatorul admin exista deja:
        if (!userRepository.existsByUsername("admin")) {
            // creaza utilizatorul admin:
            User admin = new User();
            admin.setUsername("admin");
            admin.setEmail("admin@example.com");
            admin.setPassword(passwordEncoder.encode("Parola123**")); // seteaza parola criptata
            admin.setRole("admin");

            // salveaza utilizatorul in baza de date:
            userRepository.save(admin);
            System.out.println("Admin created!");
        }
    }
}