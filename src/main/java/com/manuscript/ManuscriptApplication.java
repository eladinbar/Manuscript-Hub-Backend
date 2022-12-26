package com.manuscript;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@EnableJpaAuditing
@EnableMongoAuditing
@SpringBootApplication
public class ManuscriptApplication {

	public static void main(String[] args) {
		SpringApplication.run(ManuscriptApplication.class, args);
	}

}
