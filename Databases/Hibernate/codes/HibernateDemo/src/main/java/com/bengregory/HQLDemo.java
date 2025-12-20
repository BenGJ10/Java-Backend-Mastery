package com.bengregory;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.util.List;

public class HQLDemo {
    public static void main(String args[]){
        // Create SessionFactory with annotated class and configuration
        SessionFactory sf = new Configuration()
                .addAnnotatedClass(com.bengregory.Student.class)
                .configure()
                .buildSessionFactory();

        // Create session
        Session session = sf.openSession();

        // Normal SQL query for select statement: SELECT * FROM Student WHERE age = 20;
        // HQL query for the same is: FROM Student WHERE age = 20

        // use createQuery(String queryString, Class<T> resultType) from the session object for non parameterized queries
        // use createQuery(String queryString) from the session object for parameterized queries
        Query query  = session.createQuery("SELECT name, age FROM Student WHERE age = :age");

        // Setting the parameter
        query.setParameter("age", 20);

        // Execute query and get result list
        // We are using Object[] because we are selecting multiple fields (name and age)
        List<Object[]> students = query.getResultList();

        // Print the results
        for(Object[] student: students){
            System.out.println((String)student[0] + " " + student[1]);
        }

        // Close session and factory
        session.close();
        sf.close();
    }
}
