package com.simpleBankingApp.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan({"com.simpleBankingApp"})
@EnableJpaRepositories("com.simpleBankingApp.repository")
@EntityScan("com.simpleBankingApp.model.database")
public class SimpleBankingAppApplication {
	public static void main(String[] args) {
		SpringApplication.run(SimpleBankingAppApplication.class, args);
	}

}
