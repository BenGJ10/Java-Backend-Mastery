package com.bengregory.EmployeeManagement.service;

import com.bengregory.EmployeeManagement.EmployeeApp;
import com.bengregory.EmployeeManagement.dao.EmployeeDAO;
import com.bengregory.EmployeeManagement.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EmployeeService implements IEmployeeService {

    private EmployeeDAO employeeDAO;

    @Autowired
    public EmployeeService(EmployeeDAO employeeDAO){
        this.employeeDAO = employeeDAO;
    }

    @Override
    public List<Employee> listEmployees(){
        return employeeDAO.listEmployees();
    }

    @Override
    public Employee findEmployeeById(int id){
        return employeeDAO.findEmployeeById(id);
    }

    @Transactional
    @Override
    public Employee saveEmployee(Employee employeeToSave){
        return employeeDAO.saveEmployee(employeeToSave);
    }

    @Transactional
    @Override
    public void deleteEmployeeById(int id){
        employeeDAO.deleteEmployeeById(id);
    }
}
