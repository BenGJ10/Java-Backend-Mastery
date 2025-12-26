package com.bengregory;

import com.bengregory.config.AppConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class JavaConfigApp
{
    public static void main( String[] args )
    {   
        // Create the application context using Java-based configuration
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        Department department = context.getBean(Department.class);
        department.setName("Computer Science Engineering");
        department.printDetails();

        // Retrieve the Employee bean from the Spring container
        Employee employee = context.getBean(Employee.class);
        employee.printDetails();
    }
}
