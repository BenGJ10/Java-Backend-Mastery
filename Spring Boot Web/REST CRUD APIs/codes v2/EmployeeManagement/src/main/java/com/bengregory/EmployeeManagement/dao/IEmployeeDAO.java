package com.bengregory.EmployeeManagement.dao;

import com.bengregory.EmployeeManagement.entity.Employee;

import java.util.List;

public interface IEmployeeDAO {

    List<Employee> listEmployees();

    Employee findEmployeeById(int id);

    Employee saveEmployee(Employee employee);

    void deleteEmployeeById(int id);

}
