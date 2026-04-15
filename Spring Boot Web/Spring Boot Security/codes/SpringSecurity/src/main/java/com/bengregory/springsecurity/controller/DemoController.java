package com.bengregory.springsecurity.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

    @GetMapping("hello")
    public String greet(){
        return "Welcome Ben...";
    }

    @GetMapping("about")
    public String aboutMe(){
        return "I'm a Backend Systems Engineer";
    }
}
