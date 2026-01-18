package com.bengregory.HibernatePractice.entity;

import jakarta.persistence.*;

@Entity
@Table(name="student")
public class Student {

    // Fields
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name="first_name")
    private String firstName;

    @Column(name="last_name")
    private String lastName;

    @Column(name="department")
    private String department;

    @Column(name="total_marks")
    private int totalMarks;

    // Constructors
    public Student(){

    }

    public Student(String firstName, String lastName, String department, int totalMarks) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.department = department;
        this.totalMarks = totalMarks;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public int getTotalMarks() {
        return totalMarks;
    }

    public void setTotalMarks(int totalMarks) {
        this.totalMarks = totalMarks;
    }

    // toString() method
    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", department='" + department + '\'' +
                ", totalMarks=" + totalMarks +
                '}';
    }
}
