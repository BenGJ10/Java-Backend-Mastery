package com.bengregory;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class UpdateDemo {
    public static void main(String[] args) {

        // Create a student object
        Student student1 = new Student();
        student1.setId(5);
        student1.setName("Ashwika B Alex");
        student1.setAge(21);
        student1.setDepartment("BCom");

        // Create SessionFactory with annotated class and configuration
        SessionFactory sf = new Configuration()
                .addAnnotatedClass(Student.class)
                .configure()
                .buildSessionFactory();

        Session session = sf.openSession();

        // We need transaction for update
        Transaction transaction = session.beginTransaction();

        // Use merge to update
        session.merge(student1);

        // Commit transaction
        transaction.commit();

        session.close();
        sf.close();

        System.out.println(student1);
        System.out.println(student1.getName() + " is updated!");
    }
}