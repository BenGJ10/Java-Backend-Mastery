package com.bengregory;

public class Employee {

    private String name;
    private int age;

    public Employee() {
        System.out.println("Employee object created.");
    }

    public Employee(String name, int age) {
        System.out.println("Employee object created with parameters.");
        this.name = name;
        this.age = age;

    }

    public void printDetails(){
        System.out.println("Printing employee details...");
        System.out.println("Employee Name: " + name);
        System.out.println("Employee Age: " + age);

    }
}
