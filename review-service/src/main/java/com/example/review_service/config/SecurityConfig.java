package com.example.review_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable() // dezactivăm CSRF pentru testare în Postman
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll() // permite toate rutele fără autentificare
                );
        return http.build();
    }
}
