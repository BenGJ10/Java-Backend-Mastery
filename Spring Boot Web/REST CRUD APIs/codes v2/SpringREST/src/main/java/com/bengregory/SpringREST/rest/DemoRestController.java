package com.bengregory.SpringREST.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/demo")
public class DemoRestController {

    // Code for "/hello" endpoint
    @GetMapping("/hello")
    public String hello(){
        return "Hello Ben Gregory!";
    }
}


