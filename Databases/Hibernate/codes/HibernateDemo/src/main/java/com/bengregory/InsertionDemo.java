package com.bengregory;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class InsertionDemo {
    public static void main(String[] args) {

        // Creating a new Student object
        Student student1 = new Student();

        // Setting properties using setter methods
        student1.setId(6);
        student1.setName("Amith Ninan");
        student1.setAge(20);
        student1.setDepartment("Bcom");

        // Configuring Hibernate
        Configuration cfg = new Configuration(); // Creating Configuration object
        cfg.addAnnotatedClass(com.bengregory.Student.class); // Registering the Student class as an annotated entity
        cfg.configure(); // Configuring Hibernate using the default hibernate.cfg.xml file

        SessionFactory sf = cfg.buildSessionFactory(); // Building SessionFactory from Configuration
        Session session = sf.openSession(); // Opening a new Session

        Transaction transaction = session.beginTransaction(); // Starting a new transaction
        session.persist(student1); // Persisting the student object to the database

        transaction.commit(); // Committing the transaction
        session.close(); //
        System.out.println(student1);
        System.out.println(student1.getName() + " is added!");

    }
}