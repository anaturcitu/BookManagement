package com.unibuc.bookmanagement.services;

import com.unibuc.bookmanagement.repositories.UserRepository;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;
import com.unibuc.bookmanagement.models.User;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private static final Logger log = LoggerFactory.getLogger(CustomUserDetailsService.class);
    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Se încearcă încărcarea utilizatorului cu username: {}", username);

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> {
                    log.warn("Utilizatorul nu a fost găsit cu username: {}", username);
                    return new UsernameNotFoundException("Utilizatorul nu a fost găsit cu username: " + username);
                });

        log.info("Utilizator încărcat cu succes: {}", user.getUsername());

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword()) // parola criptata
                .authorities("ROLE_" + user.getRole().toUpperCase()) // pt roluri
                .build();
    }
}
