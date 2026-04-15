package com.bengregory.springsecurity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringSecurityApplication {

    public static void main(String[] args) {
        System.out.println("Hello Ben...");
        SpringApplication.run(SpringSecurityApplication.class, args);
    }

}
