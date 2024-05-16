package com.riwi.continental;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class ContinentalApplication {

	public static void main(String[] args) {
		SpringApplication.run(ContinentalApplication.class, args);
	}

}
