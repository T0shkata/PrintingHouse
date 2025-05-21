package com.example.printinghouse.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Employee {

    private String name;
    private String position;
    private double salary;

    public Employee() {}

    @JsonProperty("Name")
    public String getName() {
        return name;
    }

    @JsonProperty("Name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("Position")
    public String getPosition() {
        return position;
    }

    @JsonProperty("Position")
    public void setPosition(String position) {
        this.position = position;
    }

    @JsonProperty("Salary")
    public double getSalary() {
        return salary;
    }

    @JsonProperty("Salary")
    public void setSalary(double salary) {
        this.salary = salary;
    }
}
