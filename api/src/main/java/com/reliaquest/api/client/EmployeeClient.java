package com.reliaquest.api.client;


import com.reliaquest.api.dto.ApiResponse;
import com.reliaquest.api.dto.CreateEmployeeInput;
import com.reliaquest.api.dto.DeleteEmployeeInput;
import com.reliaquest.api.dto.Employee;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.UUID;

@FeignClient(name= "employeeClient" , url = "http://localhost:8112/api/v1/employee")
public interface EmployeeClient {

    @GetMapping()
    ApiResponse<List<Employee>> getEmployees();

    @GetMapping("/{id}")
    public ApiResponse<Employee> getEmployee(@PathVariable("id") UUID id);

    @PostMapping()
    public ApiResponse<Employee> createEmployee(CreateEmployeeInput employee);

    @DeleteMapping
    public ApiResponse<Boolean> deleteEmployee(DeleteEmployeeInput name);
}
