package com.bengregory.EmployeeManagement.dao;

import com.bengregory.EmployeeManagement.entity.Employee;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class EmployeeDAO implements IEmployeeDAO {
    // Define Entity Manager
    private EntityManager entityManager;

    @Autowired // SpringBoot will automatically do the constructor injection
    public EmployeeDAO(EntityManager entityManager){
        this.entityManager = entityManager;
    }

    @Override
    public List<Employee> listEmployees(){
        TypedQuery<Employee> typedQuery = entityManager.createQuery("FROM Employee", Employee.class);
        List<Employee> employees = typedQuery.getResultList();
        return employees;
    }

    @Override
    public Employee findEmployeeById(int id){
        Employee employee = entityManager.find(Employee.class, id);
        return employee;
    }

    @Override
    public Employee saveEmployee(Employee employeeToSave){
        Employee savedEmployee = entityManager.merge(employeeToSave);
        return savedEmployee;
    }

    @Override
    public void deleteEmployeeById(int id){
        Employee employee = entityManager.find(Employee.class, id);
        entityManager.remove(employee);
    }
}
