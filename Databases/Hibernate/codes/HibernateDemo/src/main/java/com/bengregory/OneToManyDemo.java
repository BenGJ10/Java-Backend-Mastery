package com.bengregory;

import com.bengregory.mapping.Employee;
import com.bengregory.mapping.Laptops;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.lang.reflect.Array;
import java.util.Arrays;

public class OneToManyDemo {
    public static void main(String args[]){

        Laptops laptop1 = new Laptops();
        laptop1.setLaptopId(1);
        laptop1.setBrand("Macbook");
        laptop1.setModel("Pro");
        laptop1.setRam(16);

        Laptops laptop2 = new Laptops();
        laptop2.setLaptopId(2);
        laptop2.setBrand("Asus");
        laptop2.setModel("TUF");
        laptop2.setRam(32);

        Employee employee1 = new Employee();
        employee1.setEmployeeId(101);
        employee1.setName("Ben Gregory");
        employee1.setTechUsed("Java");
        employee1.setLaptops(Arrays.asList(laptop1, laptop2));

        laptop1.setEmployee(employee1);
        laptop2.setEmployee(employee1);

        SessionFactory sf = new Configuration()
                .configure()
                .addAnnotatedClass(com.bengregory.mapping.Laptops.class)
                .addAnnotatedClass(com.bengregory.mapping.Employee.class)
                .buildSessionFactory();

        Session session = sf.openSession();

        Transaction transaction = session.beginTransaction();
        session.persist(laptop1);
        session.persist(laptop2);
        session.persist(employee1);

        transaction.commit();

        session.close();
        sf.close();

    }
}
