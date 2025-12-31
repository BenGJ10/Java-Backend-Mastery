package com.bengregory.JobApp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

// We can use Lombok to reduce boilerplate code for getters, setters, constructors, etc.
@Data // Will generate getters, setters, toString, equals, and hashCode methods
@NoArgsConstructor // Will generate a no-argument constructor
@AllArgsConstructor // Will generate an all-arguments constructor
@Component
public class JobPost {
    private String postId;
    private String postProfile;
    private String postDesc;
    private int reqExperience;
    private List<String> postTechStack;


}
