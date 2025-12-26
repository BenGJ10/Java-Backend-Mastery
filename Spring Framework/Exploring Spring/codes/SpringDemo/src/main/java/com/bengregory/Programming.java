package com.bengregory;

import org.springframework.stereotype.Component;

@Component
public class Programming implements Subject{
    public Programming(){
        System.out.println("Programming object created.");
    }

    @Override
    public void getDetails(){
        System.out.println("I teach programming");
    }
}
