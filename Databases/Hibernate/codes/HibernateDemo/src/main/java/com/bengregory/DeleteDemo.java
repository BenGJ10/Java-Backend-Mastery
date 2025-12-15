package com.bengregory;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class DeleteDemo {
    public static void main(String[] args) {

        // Create SessionFactory with annotated class and configuration
        SessionFactory sf = new Configuration()
                .addAnnotatedClass(Student.class)
                .configure()
                .buildSessionFactory();

        // Create session
        Session session = sf.openSession();
        
        // Retrieve student based on the id: primary key
        Student student1 = session.find(Student.class, 6);

        // We need transaction for delete
        Transaction transaction = session.beginTransaction();

        // Remove the student
        session.remove(student1);

        // Commit transaction
        transaction.commit();

        session.close();
        sf.close();

        if (student1 != null) {
            System.out.println(student1);
            System.out.println(student1.getName() + " is deleted!");
        }
        else {
            System.out.println("Student not found");
        }

    }
}