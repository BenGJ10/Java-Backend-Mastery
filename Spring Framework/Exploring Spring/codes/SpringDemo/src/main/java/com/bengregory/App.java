package com.bengregory;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App
{
    public static void main( String[] args )
    {
        // Application Context will read the spring.xml file and create the beans specified in the file
        ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml"); 
        
        // Singleton scope objects are loaded when the application context is created
        Department department = (Department) context.getBean("department");
        department.printDetails();

        // Prototype scope objects are created when requested from the application context for beans
        Employee employee1 = (Employee) context.getBean("employee");
        employee1.printDetails();

//        Employee employee2 = (Employee) context.getBean("employee");
//        employee2.printDetails();



    }
}
