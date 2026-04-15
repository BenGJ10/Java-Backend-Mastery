package com.bengregory.app.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController{

    @Value("${client.name}")
    private String name;

    @Value("${client.department}")
    private String department;


    @GetMapping("/")
    public String helloWorld(){
        return "Hello " + name + "! Welcome to " + department;
    }

    @GetMapping("/exit")
    public String exitPage(){
        return "Thank you for visiting " + department;
    }
}