package com.bengregory.EmployeeManagement.controller;

import com.bengregory.EmployeeManagement.entity.Employee;
import com.bengregory.EmployeeManagement.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tools.jackson.databind.json.JsonMapper;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class EmployeeController {

    // Use EmployeeService to handle business logic
    private EmployeeService employeeService;

    // Use JsonMapper for patch operations
    private JsonMapper jsonMapper;

    @Autowired
    public EmployeeController(EmployeeService employeeService, JsonMapper jsonMapper){
        this.employeeService = employeeService;
        this.jsonMapper = jsonMapper;
    }

    // Retrieve all employees
    @GetMapping("/employees")
    public List<Employee> retrieveEmployees(){
        return employeeService.listEmployees();
    }

    // Retrieve employee by ID
    @GetMapping("/employees/{employeeId}")
    public Employee getEmployeeById(@PathVariable int employeeId){
        Employee employee = employeeService.findEmployeeById(employeeId);

        if(employee == null){
            throw new RuntimeException("Employee id not found: " + employeeId);
        }
        return employee;
    }

    // Add new employee
    @PostMapping("/employees")
    public Employee addEmployee(@RequestBody Employee employee){
        employee.setEmpId(0);

        Employee savedEmployee = employeeService.saveEmployee(employee);
        return savedEmployee;
    }

    // Update existing employee
    @PutMapping("/employees")
    public Employee updateEmployee(@RequestBody Employee employee){
        Employee updatedEmployee = employeeService.saveEmployee(employee);
        return updatedEmployee;
    }

    // Patch existing employee - partial update (update on specific fields)
    @PatchMapping("/employees/{employeeId}")
    public Employee patchEmployee(@PathVariable int employeeId,
                                  @RequestBody Map<String, Object> patchPayLoad){

        Employee employee = employeeService.findEmployeeById(employeeId);

        if(employee == null){
            throw new RuntimeException("Employee id not found: " + employeeId);
        }
        if(patchPayLoad.containsKey(employeeId)){
            throw new RuntimeException("Employee id is not allowed in the request body: " + employeeId);
        }

        // Apply the patch to the existing employee
        Employee patchedEmployee = jsonMapper.updateValue(employee, patchPayLoad);
        // Save the patched employee
        Employee savedEmployee = employeeService.saveEmployee(patchedEmployee);
        return savedEmployee;
    }

    // Delete employee by ID
    @DeleteMapping("/employees/{employeeId}")
    public String deleteEmployee(@PathVariable int employeeId){

        Employee employee = employeeService.findEmployeeById(employeeId);

        if(employee == null){
            throw new RuntimeException("Employee id not found: " + employeeId);
        }

        // Delete the employee
        employeeService.deleteEmployeeById(employeeId);
        return "Successfully delete employeeId: " + employeeId;
    }
}
