package com.unibuc.bookmanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootApplication
@EnableAspectJAutoProxy
public class BookmanagementApplication {

	private static final Logger logger = LoggerFactory.getLogger(BookmanagementApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(BookmanagementApplication.class, args);
		logger.info("Aplicatia BookManagement a fost pornita cu succes.");
	}
}
