package com.bengregory.HibernatePractice.dao;

import com.bengregory.HibernatePractice.entity.Student;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class StudentDAO implements IStudentDAO{

    // Define field for Entity Manager
    private final EntityManager entityManager;

    // Inject Entity Manager using constructor injection
    @Autowired 
    public StudentDAO(EntityManager entityManager){
        this.entityManager = entityManager;
    }

    // Implement save() method
    @Override
    @Transactional // Use @Transactional to manage transaction boundaries
    public void save(Student student){
        entityManager.persist(student);
    }

    // Implement findById() method
    @Override
    public Student findById(int id){
        return entityManager.find(Student.class, id);
    }

    // Implement findAll() method
    @Override
    public List<Student> findAll(){
        TypedQuery<Student> typedQuery = entityManager.createQuery(
                "FROM Student ORDER BY id DESC", Student.class);
        return typedQuery.getResultList();
    }

    // Implement findByDepartment()
    @Override
    public List<Student> findByDepartment(String department){
        TypedQuery<Student> typedQuery = entityManager.createQuery(
                "FROM Student WHERE department =: dept ORDER BY id ASC", Student.class);
        typedQuery.setParameter("dept", department);
        return typedQuery.getResultList();
    }

    // Implement update() method
    @Override
    @Transactional
    public void update(Student student){
        entityManager.merge(student);
    }

    // Implement delete() method
    @Override
    @Transactional
    public void delete(int Id){
        Student student = findById(Id);
        if(student != null) entityManager.remove(student);
    }
}
