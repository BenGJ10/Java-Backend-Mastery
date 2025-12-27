package com.bengregory.app;

import org.springframework.stereotype.Component;

@Component
public class Desktop implements Computer{

    @Override
    public void showDetails() {

    }

    @Override
    public void compile(){
        System.out.println("I am compiling using Desktop");
    }
}
