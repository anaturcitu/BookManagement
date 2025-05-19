package com.unibuc.bookmanagement.config;

import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.WebRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

@Configuration
public class ErrorConfig {

    private static final Logger logger = LoggerFactory.getLogger(ErrorConfig.class);

    @Bean
    public DefaultErrorAttributes errorAttributes() {
        return new DefaultErrorAttributes() {
            @Override
            public Map<String, Object> getErrorAttributes(WebRequest webRequest, ErrorAttributeOptions options) {
                logger.warn("A fost generată o eroare - se colectează atributele de eroare.");
                // pot modifica aici ce informatii trimit mai departe in model
                return super.getErrorAttributes(webRequest, options.including(
                        ErrorAttributeOptions.Include.MESSAGE,
                        ErrorAttributeOptions.Include.EXCEPTION
                ));
            }
        };
    }
}
