package com.bengregory.app.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController{

    @GetMapping("/")
    public String helloWorld(){
        return "Hello Ben! Welcome to Spring Boot..";
    }

    @GetMapping("/exit")
    public String exitPage(){
        return "Thank you for visiting us..";
    }
}