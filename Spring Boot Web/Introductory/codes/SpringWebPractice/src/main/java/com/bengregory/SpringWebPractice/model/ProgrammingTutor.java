package com.bengregory.SpringWebPractice.model;

import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

@Primary   // Indicates that this is the primary bean to be injected when multiple candidates are present
@Component // Marks the class as a spring bean, makes it available for dependency injection
@Lazy      // Defers bean initialization until it is needed

public class ProgrammingTutor implements Tutor {
    
    public ProgrammingTutor(){
        System.out.println(getClass().getSimpleName() + " has been invoked!");
    }

    // PostConstruct is used to run initialization logic after the bean is constructed
    @PostConstruct
    public void startupMessage(){
        System.out.println(getClass().getSimpleName() + " is ready to help you!");
    }

    @Override
    public String getAdvice(){
        return "Practice Data Structures and Algorithms";
    }

    // PreDestroy can be used to define cleanup logic before the bean is destroyed
    @PreDestroy
    public void cleanupMessage(){
        System.out.println(getClass().getSimpleName() + " is signing off. Happy Coding!");
    }

}
