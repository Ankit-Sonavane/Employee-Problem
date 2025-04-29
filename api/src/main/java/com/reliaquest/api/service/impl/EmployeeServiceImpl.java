package com.reliaquest.api.service.impl;

import com.reliaquest.api.client.EmployeeClient;
import com.reliaquest.api.dto.ApiResponse;
import com.reliaquest.api.dto.CreateEmployeeInput;
import com.reliaquest.api.dto.DeleteEmployeeInput;
import com.reliaquest.api.dto.Employee;
import com.reliaquest.api.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    public EmployeeServiceImpl(EmployeeClient employeeClient){
        this.employeeClient = employeeClient;
    }
    private final EmployeeClient employeeClient;

    @Override
    public List<Employee> getAllEmployees() {
        log.info("Fetching all employees...");
        return employeeClient.getEmployees().getData();
    }

    @Override
    public List<Employee> searchEmployeesByName(String name) {
        try {
            log.info("Fetching employee with name: {}", name);
            return employeeClient.getEmployees().getData().stream()
                    .filter(e -> e.getEmployee_name().toLowerCase().contains(name.toLowerCase()))
                    .collect(Collectors.toList());
        }catch (HttpClientErrorException.NotFound ex){
            log.warn("Employee not found with name: {}", name);
            return null;
        }
    }

    @Override
    public Optional<Employee> getEmployeeById(UUID id) {
        try {
        log.info("Fetching employee with id: {}", id);
        return Optional.of(employeeClient.getEmployee(id).getData());
        }catch (HttpClientErrorException.NotFound ex){
            log.warn("Employee not found with name: {}", id);
            return Optional.empty();
        }
    }

    @Override
    public int getHighestSalary() {
        return employeeClient.getEmployees().getData().stream()
                .mapToInt(Employee::getEmployee_salary)
                .max()
                .orElse(0);
    }

    @Override
    public List<String> getTop10HighestEarningEmployeeNames() {

        return employeeClient.getEmployees().getData().stream()
                .sorted((e1, e2) -> Integer.compare(e2.getEmployee_salary(), e1.getEmployee_salary()))
                .limit(10)
                .map(Employee::getEmployee_name)
                .collect(Collectors.toList());
    }

    @Override
    public Employee createEmployee(CreateEmployeeInput employee) {
        log.info("Creating new employee: {}", employee);
        return employeeClient.createEmployee(employee).getData();
    }

    @Override
    public String deleteEmployeeById(DeleteEmployeeInput input) {
        try {
            log.info("Deleting employee with name: {}", input);
            ApiResponse<Boolean> deleted = employeeClient.deleteEmployee(input);
            if (!deleted.getData()) {
                throw new RuntimeException("Employee not found with name: " + input);
            }
            return input.getName();
        } catch (HttpClientErrorException.NotFound e) {
            log.warn("Employee not found with name: {}", input);
            return null;
        }
    }
}
