package com.bengregory.mapping;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.List;

@Entity
public class Employee {

    @Id
    private int employeeId;
    private String name;
    private String techUsed;

    @OneToMany(mappedBy = "employee")
    private List<Laptops> laptops;

    public List<Laptops> getLaptops() {
        return laptops;
    }

    public void setLaptops(List<Laptops> laptops) {
        this.laptops = laptops;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTechUsed() {
        return techUsed;
    }

    public void setTechUsed(String techUsed) {
        this.techUsed = techUsed;
    }
}
