package com.bengregory.config;

import com.bengregory.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;


// This class is used to define Spring beans using Java-based configuration
@Configuration
@ComponentScan("com.bengregory")
public class AppConfig {

    @Bean
    public Employee employee(Subject subject){ // We can pass dependencies as method parameters
        Employee obj = new Employee();
        obj.setName("Ben Gregory");
        obj.setAge(20);
        obj.setSubject(subject);
        return obj;
    }

    @Bean
    @Primary // This annotation gives higher preference to this bean when multiple beans of the same type are present
    public Programming programming(){
        return new Programming();
    }

    @Bean
    public Development development(){
        return new Development();
    }

    // We need to create and return a Department bean, what Java will do for us is manage it inside Spring container
    @Bean
    public Department department(){
        return new Department();
    }
}
