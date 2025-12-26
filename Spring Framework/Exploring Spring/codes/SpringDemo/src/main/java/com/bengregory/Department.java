package com.bengregory;

import org.springframework.stereotype.Component;

@Component
public class Department {

    private String name;

    public Department() {
        System.out.println("Department object created.");
    }

    public Department(String name) {
        System.out.println("Department object created.");
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void printDetails(){
        System.out.println("Printing department details...");
        System.out.println("Department: " + getName());
    }
    
}


