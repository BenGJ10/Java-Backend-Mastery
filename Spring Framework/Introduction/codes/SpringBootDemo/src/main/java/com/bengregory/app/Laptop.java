package com.bengregory.app;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class Laptop implements Computer{

    @Override
    public void showDetails() {

    }

    @Override
    public void compile(){
        System.out.println("I am compiling using Laptop...");
    }
}
