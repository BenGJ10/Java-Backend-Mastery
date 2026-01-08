package com.bengregory.SpringWebPractice.model;

public class OSTutor implements Tutor{

    public OSTutor(){
        System.out.println(getClass().getSimpleName() + " has been invoked!");
    }

    @Override
    public String getAdvice() {
        return "Understand how HTTP and HTTPS works";
    }
}
