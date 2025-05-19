package com.unibuc.bookmanagement.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Configuration
public class SwaggerConfig {

    private static final Logger logger = LoggerFactory.getLogger(SwaggerConfig.class);

    @Bean
    public OpenAPI customOpenAPI() {
        logger.info("Configurarea Swagger/OpenAPI a fost încărcată cu succes.");
        return new OpenAPI()
                .info(new Info()
                        .title("Book Management API")
                        .version("1.0")
                        .description("API for managing books, authors, genres, reviews"));
    }
}
