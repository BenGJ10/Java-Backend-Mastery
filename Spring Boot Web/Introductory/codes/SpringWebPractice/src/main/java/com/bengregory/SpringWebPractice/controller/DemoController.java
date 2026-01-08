package com.bengregory.SpringWebPractice.controller;
import com.bengregory.SpringWebPractice.model.Tutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

// RestController to handle web requests
@RestController
public class DemoController {

    // Injecting value from application.properties
    @Value("${student.name}")
    private String name;
    private final Tutor tutor;

    // Constructor Injection of Tutor bean with Qualifier
    @Autowired
    public DemoController(@Qualifier("developmentTutor") Tutor tutor){
        System.out.println(getClass().getSimpleName() + " has been invoked!");
        this.tutor = tutor;
    }

    // Endpoint to return a welcome message
    @GetMapping("/")
    public String home(){
        return "Hi " + name +", Welcome to the Demo App!";
    }

    // Endpoint to get advice from the injected Tutor bean
    @GetMapping("/advice")
    public String getAdvice(){
        return tutor.getAdvice();
    }
}
