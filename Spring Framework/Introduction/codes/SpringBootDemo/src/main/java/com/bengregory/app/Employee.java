package com.bengregory.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

// To make this class a Spring Bean, we annotate it with @Component
@Component
public class Employee {
    @Value("Ben Gregory")
    private String name;
    @Value("20")
    private int age;
    private Computer computer;

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

    public Computer getComputer() {
        return computer;
    }

    @Autowired
    public void setComputer(Computer computer) {
        this.computer = computer;
    }

    public void showEmployee()
    {
        System.out.println("Printing employee details...");
        System.out.println("Employee name: " + getName());
        System.out.println("Employee age: " + getAge());
        computer.compile();
    }

}
