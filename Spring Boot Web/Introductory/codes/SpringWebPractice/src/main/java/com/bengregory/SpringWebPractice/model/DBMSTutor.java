package com.bengregory.SpringWebPractice.model;

public class DBMSTutor implements Tutor{

    public DBMSTutor(){
        System.out.println(getClass().getSimpleName() + " has been invoked!");
    }

    @Override
    public String getAdvice(){
        return "Dive deep into Database Normalization";
    }
}