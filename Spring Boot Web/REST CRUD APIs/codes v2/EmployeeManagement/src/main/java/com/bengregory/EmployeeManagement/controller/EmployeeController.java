package com.bengregory.EmployeeManagement.controller;

import com.bengregory.EmployeeManagement.entity.Employee;
import com.bengregory.EmployeeManagement.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class EmployeeController {

    private EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService){
        this.employeeService = employeeService;
    }

    @GetMapping("/employees")
    public List<Employee> retrieveEmployees(){
        return employeeService.listEmployees();
    }

    @GetMapping("/employees/{employeeId}")
    public Employee getEmployeeById(@PathVariable int employeeId){
        Employee employee = employeeService.findEmployeeById(employeeId);

        if(employee == null){
            throw new RuntimeException("Employee id not found: " + employeeId);
        }
        return employee;
    }

    @PostMapping("/employees")
    public Employee addEmployee(@RequestBody Employee employee){
        employee.setEmpId(0);

        Employee savedEmployee = employeeService.saveEmployee(employee);
        return savedEmployee;
    }

    @PutMapping("/employees")
    public Employee updateEmployee(@RequestBody Employee employee){
        Employee updatedEmployee = employeeService.saveEmployee(employee);
        return updatedEmployee;
    }

}
