// We use @Configuration to mark this class as a source of bean definitions for the application context.
// The @Bean annotation tells Spring that a method annotated with @Bean will return an object that should be registered as a bean in the Spring application context.

package com.bengregory.SpringWebPractice.config;
import com.bengregory.SpringWebPractice.model.DBMSTutor;
import com.bengregory.SpringWebPractice.model.OSTutor;
import com.bengregory.SpringWebPractice.model.Tutor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TutorConfig {

    @Bean("os")
    public Tutor osTutor(){
        return new OSTutor();
    }

    @Bean("dbms")
    public Tutor dbmsTutor(){
        return new DBMSTutor();
    }
}
