package com.bengregory.EmployeeManagement.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
public class SecurityConfig {

    @Bean
    public UserDetailsManager userDetailsManager(DataSource dataSource){
        // Spring Security will use JDBC to store and retrieve user credentials and roles
        // We do not need to write any SQL queries, Spring Security will generate them based on its default schema
        return new JdbcUserDetailsManager(dataSource);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.authorizeHttpRequests(configurer ->
                configurer
                        .requestMatchers(HttpMethod.GET, "/api/employees").hasRole("EMPLOYEE")
                        .requestMatchers(HttpMethod.GET, "/api/employees/**").hasRole("EMPLOYEE")
                        .requestMatchers(HttpMethod.POST, "/api/employees").hasRole("MANAGER")
                        .requestMatchers(HttpMethod.PUT, "/api/employees/**").hasRole("MANAGER")
                        .requestMatchers(HttpMethod.PATCH, "/api/employees/**").hasRole("MANAGER")
                        .requestMatchers(HttpMethod.DELETE, "/api/employees/**").hasRole("ADMIN")

        );

        // Use basic HTTP authentication
        http.httpBasic(Customizer.withDefaults());

        // Disable Cross Site Request Forgery
        http.csrf(csrf -> csrf.disable());

        return http.build();
    }

    /*
    Hard-coded User Auth using InMemoryDetailsManager
    @Bean
    public InMemoryUserDetailsManager userDetailsManager(){

        UserDetails employee = User.builder()
                .username("John")
                .password("{noop}john@123")
                .roles("EMPLOYEE")
                .build();

        UserDetails manager = User.builder()
                .username("Jacob")
                .password("{noop}jacob@123")
                .roles("EMPLOYEE", "MANAGER")
                .build();

        UserDetails admin = User.builder()
                .username("Lucas")
                .password("{noop}lucas@123")
                .roles("EMPLOYEE", "MANAGER", "ADMIN")
                .build();

        return new InMemoryUserDetailsManager(employee, manager, admin);
    }
*/
}
