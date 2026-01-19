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

			updateStudent(studentDAO, 1);

			deleteStudent(studentDAO, 8);

		};
	}

	private void createStudent(StudentDAO studentDAO) {
		// Create the student object
		System.out.println("Creating a new Student object...");
		Student student = new Student("Joe", "Keery", "AI", 1000);

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
		else {
			System.out.println("Student full name: " + student.getFirstName() + " " + student.getLastName());
		}
	}

	private void retrieveAllStudents(StudentDAO studentDAO) {
		// Retrieve all Students
		System.out.println("Retrieving all Students from the database...");
		List<Student> studentList = studentDAO.findAll();

		// Display all Students
		if(studentList.isEmpty()){
			System.out.println("Database is empty! Nothing to retrieve.");
		}
		else{
			for(Student student: studentList){
				System.out.println("Student ID: " + student.getId() +
						", Student Name: " + student.getFirstName() + " " + student.getLastName() +
						", Department: " + student.getDepartment() +
						", Total Marks: " + student.getTotalMarks());
			}
		}
	}

	private void retrieveStudentsByDepartment(StudentDAO studentDAO, String department) {
		// Retrieve Students by Department
		System.out.println("Retrieving all Students from " + department + " department");
		List<Student> studentList = studentDAO.findByDepartment(department);

		// Display all Students
		if(studentList.isEmpty()){
			System.out.println("Student details from " + department + " department not added!");
		}
		else {
			for(Student student: studentList){
				System.out.println("Student ID: " + student.getId() +
						", Student Name: " + student.getFirstName() + " " + student.getLastName() +
						", Department: " + student.getDepartment() +
						", Total Marks: " + student.getTotalMarks());
			}
		}
	}

	private void updateStudent(StudentDAO studentDAO, int id){
		// Retrieve the student
		System.out.println("Getting the Student information having id: " + id);
		Student student = studentDAO.findById(id);

		if(student == null){
			System.out.println("Unable to retrieve student credentials! Try again.");
		}
		else {
			// Change the student information
			student.setTotalMarks(1200);
			student.setDepartment("CSE");

			// Update the student
			System.out.println("Updating the Student information...");
			studentDAO.update(student);

			// Display the update
			System.out.println("Successfully updated the Student information");
			System.out.println("Student ID: " + student.getId() +
					", Student Name: " + student.getFirstName() + " " + student.getLastName() +
					", Department: " + student.getDepartment() +
					", Total Marks: " + student.getTotalMarks());
		}
	}

	private void deleteStudent(StudentDAO studentDAO, int id){
		// Check if the Student exists
		Student student = studentDAO.findById(id);

		if(student == null){
			System.out.println("Unable to delete Student! Try again.");
		}
		else {
			// Delete the student
			System.out.println("Deleting the Student information...");
			studentDAO.delete(id);

			// Display the deletion
			System.out.println("Successfully deleted Student having id: " + id);
		}
	}
}
