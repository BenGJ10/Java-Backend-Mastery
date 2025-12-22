package com.bengregory.app;

import org.springframework.stereotype.Component;

// To make this class a Spring Bean, we annotate it with @Component
@Component
public class Employee {

    public void showEmployee() {
        System.out.println("Employee details displayed.");
    }

    public void employeeSkills() {
        System.out.println("Employee skills displayed.");
    }
    
}
