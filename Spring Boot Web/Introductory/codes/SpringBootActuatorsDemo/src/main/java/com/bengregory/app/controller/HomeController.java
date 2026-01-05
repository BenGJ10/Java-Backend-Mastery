package com.bengregory.app.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

// Use RestController to create RESTful web services
@RestController
public class HomeController{

    // Inject values from application properties using @Value annotation
    @Value("${client.name}")
    private String name;

    @Value("${client.department}")
    private String department;

    // Map HTTP GET requests to the root URL ("/") and "/home"
    @GetMapping({"/", "/home"})
    public String helloWorld(){
        return "Hello " + name + "! Welcome to " + department;
    }

    // Map HTTP GET requests to the "/exit" URL
    @GetMapping("/exit")
    public String exitPage(){
        return "Thank you for visiting " + department;
    }
}