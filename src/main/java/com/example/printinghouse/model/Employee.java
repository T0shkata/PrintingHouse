package com.example.printinghouse.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Employee {

    @JsonProperty("Name")
    private String Name;

    @JsonProperty("Position")
    private String Position;

    @JsonProperty("Salary")
    private double Salary;

    public Employee() {}

    public String getName() { return Name; }
    public void setName(String Name) { this.Name = Name; }

    public String getPosition() { return Position; }
    public void setPosition(String Position) { this.Position = Position; }

    public double getSalary() { return Salary; }
    public void setSalary(double Salary) { this.Salary = Salary; }
}
