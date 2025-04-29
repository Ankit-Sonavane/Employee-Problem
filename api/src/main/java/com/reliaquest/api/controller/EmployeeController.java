package com.reliaquest.api.controller;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.reliaquest.api.dto.CreateEmployeeInput;
import com.reliaquest.api.dto.DeleteEmployeeInput;
import com.reliaquest.api.dto.Employee;
import com.reliaquest.api.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.gson.GsonBuilderCustomizer;
import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.GsonBuilderUtils;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@RestController
public class EmployeeController implements IEmployeeController {

    @Autowired
    public EmployeeController(EmployeeService employeeService){
        this.employeeService = employeeService;
    }

    private final EmployeeService employeeService;

    Gson gson = new GsonBuilder().create();

    @Override
    public ResponseEntity<List> getAllEmployees() {
        List<Employee> employees = employeeService.getAllEmployees();
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List> getEmployeesByNameSearch(String searchString) {
        List<Employee> employeesByName = employeeService.searchEmployeesByName(searchString);
        return new ResponseEntity<>(employeesByName, HttpStatus.OK);
    }

    @Override
    public ResponseEntity getEmployeeById(String id) {
        Employee employee = employeeService.getEmployeeById(UUID.fromString(id)).get();
        return new ResponseEntity<>(employee, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Integer> getHighestSalaryOfEmployees() {
        Integer highestSalaryofEmployees = employeeService.getHighestSalary();
        return new ResponseEntity<>(highestSalaryofEmployees, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<String>> getTopTenHighestEarningEmployeeNames() {
        List<String> topTenHighestEarningEmployeeNames = employeeService.getTop10HighestEarningEmployeeNames();
        return new ResponseEntity<>(topTenHighestEarningEmployeeNames, HttpStatus.OK);
    }

    @Override
    public ResponseEntity createEmployee(Object employeeInput) {

        String json = gson.toJson(employeeInput);
        CreateEmployeeInput employee = gson.fromJson(json, CreateEmployeeInput.class);
        Employee employeeDTO = employeeService.createEmployee(employee);
        return new ResponseEntity<>(employeeDTO, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> deleteEmployeeById(String id) {
        Employee employee = employeeService.getEmployeeById(UUID.fromString(id)).get();
        HashMap map = new HashMap<>();
        map.put("name", employee.getEmployee_name());
        String json = gson.toJson(map);
        String employeeName = employeeService.deleteEmployeeById(gson.fromJson(json, DeleteEmployeeInput.class));
        return new ResponseEntity<>(employeeName, HttpStatus.OK);
    }
}
