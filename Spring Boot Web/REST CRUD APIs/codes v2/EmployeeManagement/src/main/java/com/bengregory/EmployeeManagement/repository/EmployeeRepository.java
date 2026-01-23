package com.bengregory.EmployeeManagement.repository;

import com.bengregory.EmployeeManagement.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    
    /*
        We can make use of JpaRepository methods for CRUD operations here.
        No need to explicitly define methods like save(), findById(), findAll(), deleteById() etc.
        JpaRepository provides these methods out of the box.

        Additional custom query methods can be defined here if needed.
        The core difference between JpaRepository and DAO pattern is that JpaRepository is part of Spring Data JPA
        and provides a higher level of abstraction for data access, reducing boilerplate code.
        This practice is preferred in modern Spring applications for its simplicity and efficiency.
     */
}
