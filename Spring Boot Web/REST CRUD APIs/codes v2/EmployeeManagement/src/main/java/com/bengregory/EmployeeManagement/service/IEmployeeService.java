package com.bengregory.EmployeeManagement.service;

import com.bengregory.EmployeeManagement.entity.Employee;

import java.util.List;

public interface IEmployeeService {

    List<Employee> listEmployees();

    Employee findEmployeeById(int id);

    Employee saveEmployee(Employee employee);

    void deleteEmployeeById(int id);
}
