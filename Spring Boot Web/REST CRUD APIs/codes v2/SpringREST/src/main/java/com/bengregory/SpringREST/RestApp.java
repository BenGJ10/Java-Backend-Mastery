package com.bengregory.SpringREST;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RestApp {

	public static void main(String[] args) {
		System.out.println("Running the Spring Boot App...");
		SpringApplication.run(RestApp.class, args);
	}

}
