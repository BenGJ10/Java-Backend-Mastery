package com.bengregory.EmployeeManagement.service;

import com.bengregory.EmployeeManagement.entity.Employee;
import com.bengregory.EmployeeManagement.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeRepoService implements IEmployeeService {

    private EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeRepoService(EmployeeRepository employeeRepository){
        this.employeeRepository = employeeRepository;
    }

    @Override
    public List<Employee> listEmployees(){
        return employeeRepository.findAll();
    }

    @Override
    public Employee findEmployeeById(int id){
        Optional<Employee> result = employeeRepository.findById(id);
        Employee employee = null;

        if(result.isPresent()) employee = result.get();
        else throw new RuntimeException("Employee id not found: " + id);

        return employee;

        /*return employeeRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Employee id not found: " + id));*/
    }

    @Override
    public Employee saveEmployee(Employee employeeToSave){
        return employeeRepository.save(employeeToSave);
    }

    @Override
    public void deleteEmployeeById(int id){
        employeeRepository.deleteById(id);
    }

}
