package com.bengregory.SpringREST.rest;

import com.bengregory.SpringREST.entity.Student;
import com.bengregory.SpringREST.utils.StudentNotFoundException;
import jakarta.annotation.PostConstruct;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class StudentRestController {

    private List<Student> students;

    // Use PostConstruct to load the student data ... only once!
    @PostConstruct
    public void loadData(){
        students = new ArrayList<>();
        students.add(new Student(0, "Ben", "Gregory", "CSE"));
        students.add(new Student(1, "Pavan", "Viju", "CSE"));
        students.add(new Student(2, "Ashwika", "B Alex", "BCom"));
        students.add(new Student(3, "Allen", "V Pothen", "MEC"));
    }

    // Define endpoint for "/students" - return list of students
    @GetMapping("/students")
    public List<Student> getStudents(){
        return students;
    }

    // Define endpoint for "/students/{studentId}" - return a student
    @GetMapping("/students/{studentId}")
    public Student getStudent(@PathVariable int studentId){

        if((studentId >= students.size()) || (studentId < 0)) {
            throw new StudentNotFoundException("Student Id not found: " + studentId);
        }
        return students.get(studentId);
    }

}
