package com.bengregory;

public class Employee {

    private String name;
    private int age;
    private Subject subject;

    public Employee() {
        System.out.println("Employee object created without parameters.");
    }

    public Employee(String name, int age, Subject subject) {
        System.out.println("Employee object created with parameters.");
        this.name = name;
        this.age = age;
        this.subject = subject;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public void printDetails(){
        System.out.println("Printing employee details...");
        System.out.println("Employee Name: " + name);
        System.out.println("Employee Age: " + age);
        subject.getDetails();
    }
}
