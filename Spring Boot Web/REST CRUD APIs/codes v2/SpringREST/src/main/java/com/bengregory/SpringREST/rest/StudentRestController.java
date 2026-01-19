package com.bengregory.SpringREST.rest;

import com.bengregory.SpringREST.entity.Student;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class StudentRestController {

    @GetMapping("/students")
    public List<Student> getStudents(){

        List<Student> students = new ArrayList<>();
        students.add(new Student(1, "Ben", "Gregory", "CSE"));
        students.add(new Student(2, "Ashwika", "B Alex", "BCom"));
        students.add(new Student(3, "Allen", "V Pothen", "MEC"));

        return students;
    }
}
