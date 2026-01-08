package com.bengregory.SpringWebPractice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringWebPracticeApplication {

	public static void main(String[] args) {
		// Bootstrapping Spring Boot Application
		SpringApplication.run(SpringWebPracticeApplication.class, args);
		System.out.println("Hello Ben!");
	}

}
