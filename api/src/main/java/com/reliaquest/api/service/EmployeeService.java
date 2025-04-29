package com.reliaquest.api.service;

import com.reliaquest.api.dto.CreateEmployeeInput;
import com.reliaquest.api.dto.DeleteEmployeeInput;
import com.reliaquest.api.dto.Employee;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EmployeeService {

    public List<Employee> getAllEmployees();

    public List<Employee> searchEmployeesByName(String nameFragment);

    public Optional<Employee> getEmployeeById(UUID id);

    public int getHighestSalary();

    public List<String> getTop10HighestEarningEmployeeNames();

    public Employee createEmployee(CreateEmployeeInput employee);

    public String deleteEmployeeById(DeleteEmployeeInput name);
}
