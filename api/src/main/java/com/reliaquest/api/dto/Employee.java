package com.reliaquest.api.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class Employee {

    private UUID id;
    private String employee_name;
    private Integer employee_salary;
    private Integer employee_age;
    private String employee_title;
    private String employee_email;

    public Employee() {
    }

    public Employee(UUID id, String employee_name, Integer employee_salary, Integer employee_age, String employee_title, String employee_email) {
        this.id = id;
        this.employee_name = employee_name;
        this.employee_salary = employee_salary;
        this.employee_age = employee_age;
        this.employee_title = employee_title;
        this.employee_email = employee_email;
    }
}
