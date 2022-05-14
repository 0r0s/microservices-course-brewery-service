package com.novilabs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class BreweryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BreweryServiceApplication.class, args);
	}
}
