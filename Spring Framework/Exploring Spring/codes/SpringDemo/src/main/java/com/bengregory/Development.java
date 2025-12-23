package com.bengregory;

public class Development implements Subject{
    public Development() {
        System.out.println("Development object created.");
    }

    @Override
    public void getDetails(){
        System.out.println("I teach backend development");
    }
}
