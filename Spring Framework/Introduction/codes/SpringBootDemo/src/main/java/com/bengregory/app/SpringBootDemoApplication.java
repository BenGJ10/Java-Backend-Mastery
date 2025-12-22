package com.bengregory.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class SpringBootDemoApplication {

	public static void main(String[] args) {
		
		// ApplicationContext is the Spring IoC container that holds the beans
		// We use SpringApplication.run() to launch the application
		ApplicationContext context = SpringApplication.run(SpringBootDemoApplication.class, args);
		
		Employee employee = context.getBean(Employee.class);
		employee.showEmployee();
	}

}