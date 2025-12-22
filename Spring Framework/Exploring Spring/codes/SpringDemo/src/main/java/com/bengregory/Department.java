package com.bengregory;

public class Department {

    private String name;

    public Department() {
        System.out.println("Department object created.");
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


