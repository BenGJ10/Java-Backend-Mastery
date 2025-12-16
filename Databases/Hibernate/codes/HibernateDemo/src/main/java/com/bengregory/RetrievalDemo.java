package com.bengregory;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class RetrievalDemo {
    public static void main(String[] args) {
        
        // Create SessionFactory with annotated class and configuration
        SessionFactory sf = new Configuration()
                .addAnnotatedClass(com.bengregory.Student.class)
                .configure()
                .buildSessionFactory();
        
        // Create session
        Session session = sf.openSession();
        
        // We don't need a transaction for retrieval
        Student student2 = session.find(Student.class, 1);
        
        // Close session
        session.close();
        sf.close();

        if (student2 != null) {
            System.out.println(student2.getName() + " is retrieved!");
        }
        else {
            System.out.println("Student not found");
        }

    }
}