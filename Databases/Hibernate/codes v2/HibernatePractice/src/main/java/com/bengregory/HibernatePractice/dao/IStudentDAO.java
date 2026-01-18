package com.bengregory.HibernatePractice.dao;

import com.bengregory.HibernatePractice.entity.Student;

import java.util.List;

public interface IStudentDAO {

    void save(Student student);

    Student findById(int Id);

    List<Student> findAll();

    List<Student> findByDepartment(String department);
}
