package com.unibuc.bookmanagement.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.extras.springsecurity6.dialect.SpringSecurityDialect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Configuration
public class ThymeleafConfig {

    private static final Logger logger = LoggerFactory.getLogger(ThymeleafConfig.class);

    @Bean
    public SpringSecurityDialect springSecurityDialect() {
        logger.info("Dialectul Spring Security pentru Thymeleaf a fost înregistrat.");
        return new SpringSecurityDialect();
    }

}
