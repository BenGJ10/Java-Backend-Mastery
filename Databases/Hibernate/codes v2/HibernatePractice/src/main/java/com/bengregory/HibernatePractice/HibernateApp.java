package com.bengregory.HibernatePractice;

import com.bengregory.HibernatePractice.dao.StudentDAO;
import com.bengregory.HibernatePractice.entity.Student;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class HibernateApp {

	public static void main(String[] args) {
		SpringApplication.run(HibernateApp.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(StudentDAO studentDAO){
		return runner ->{

			createStudent(studentDAO);

			retrieveStudent(studentDAO, 1);

			retrieveAllStudents(studentDAO);

			retrieveStudentsByDepartment(studentDAO, "CSE");
			
		};
	}

	private void createStudent(StudentDAO studentDAO) {
		// Create the student object
		System.out.println("Creating a new Student object...");
		Student student = new Student("Ben", "Gregory", "CSE", 1200);

		// Save the student object
		System.out.println("Saving the Student object...");
		studentDAO.save(student);

		// Display details of the student object
		System.out.println("Student object saved! Student name: " + student.getFirstName() + " Student ID: " + student.getId());
	}

	private void retrieveStudent(StudentDAO studentDAO, int id){
		// Retrieve student based on the id: primary key
		System.out.println("Retrieving Student details with id: " + id);
		Student student = studentDAO.findById(id);

		// Display student details
		if(student == null){
			System.out.println("Unable to retrieve student credentials! Try again.");
		}
		else{
			System.out.println("Student full name: " + student.getFirstName() + " " + student.getLastName());
		}
	}

	private void retrieveAllStudents(StudentDAO studentDAO) {
		// Retrieve all Students
		System.out.println("Retrieving all Students from the database...");
		List<Student> studentList = studentDAO.findAll();

		// Display all Students
		for(Student student: studentList){
			System.out.println("Student ID: " + student.getId() +
					", Student Name: " + student.getFirstName() + " " + student.getLastName());
		}
	}

	private void retrieveStudentsByDepartment(StudentDAO studentDAO, String department) {
		// Retrieve Students by Department
		System.out.println("Retrieving all Students from " + department + " department");
		List<Student> studentList = studentDAO.findByDepartment(department);

		// Display all Students
		for(Student student: studentList){
			System.out.println("Student ID: " + student.getId() +
					", Student Name: " + student.getFirstName() + " " + student.getLastName());
		}
	}
}
