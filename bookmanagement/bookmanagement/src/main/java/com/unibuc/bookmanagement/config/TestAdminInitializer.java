package com.unibuc.bookmanagement.config;

import com.unibuc.bookmanagement.models.User;
import com.unibuc.bookmanagement.repositories.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@Profile("test")  // rulează doar când profilul activ este "test"
public class TestAdminInitializer {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public TestAdminInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void init() {
        if (!userRepository.existsByUsername("admin")) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setEmail("admin@example.com");
            admin.setPassword(passwordEncoder.encode("Parola123**"));  // parola criptată
            admin.setRole("ADMIN");  // poți pune "ADMIN" cu majuscule dacă vrei să fie consistent
            
            userRepository.save(admin);
            System.out.println("Admin user created for TEST profile.");
        }
    }
}
