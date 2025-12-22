package com.bengregory;

public class Employee {

    public String name;

    public Employee() {
        System.out.println("Employee object created.");
    }

    public void printDetails(){
        System.out.println("Printing employee details...");
        System.out.println("Employee Name: " + name);
    }
}
