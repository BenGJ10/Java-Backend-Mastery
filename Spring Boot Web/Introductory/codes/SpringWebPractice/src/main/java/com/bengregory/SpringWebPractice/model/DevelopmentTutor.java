package com.bengregory.SpringWebPractice.model;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

@Lazy
@Component
public class DevelopmentTutor implements Tutor {

    public DevelopmentTutor(){
        System.out.println(getClass().getSimpleName() + " has been invoked!");
    }

    // PostConstruct is used to run initialization logic after the bean is constructed
    @PostConstruct
    public void startupMessage(){
        System.out.println(getClass().getSimpleName() + " is ready to help you!");
    }


    @Override
    public String getAdvice(){
        return "Understand the fundamentals of Backend Development";
    }

    // PreDestroy can be used to define cleanup logic before the bean is destroyed
    @PreDestroy
    public void cleanupMessage(){
        System.out.println(getClass().getSimpleName() + " is signing off. Happy Coding!");
    }
}
