package com.uidt.ApiResful.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uidt.ApiResful.model.Employee;
import com.uidt.ApiResful.service.EmployeeService;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployee(@PathVariable Long id) {
        Optional<Employee> employee = employeeService.getEmployee(id);
        return employee.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build()); 
    }

    @GetMapping
    public ResponseEntity<Iterable<Employee>> getAllEmployees() {
        Iterable<Employee> employees = employeeService.getEmployees();
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    @PostMapping 
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) {
        Employee savedEmployee = employeeService.saveEmployee(employee);
        return new ResponseEntity<>(savedEmployee, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody Employee employeeDetails) {
        Optional<Employee> employeeOptional = employeeService.getEmployee(id);
        if (!employeeOptional.isPresent()) {
            return ResponseEntity.notFound().build(); 
        }

        Employee employeeToUpdate = employeeOptional.get();
        employeeToUpdate.setFirstName(employeeDetails.getFirstName());
        employeeToUpdate.setLastName(employeeDetails.getLastName());
        employeeToUpdate.setMail(employeeDetails.getMail());
        employeeToUpdate.setPassword(employeeDetails.getPassword());
        Employee updatedEmployee = employeeService.saveEmployee(employeeToUpdate);
        return new ResponseEntity<>(updatedEmployee, HttpStatus.OK);
    } 

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        if (!employeeService.getEmployee(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        employeeService.deleteEmployee(id);
        return ResponseEntity.noContent().build(); 
    }
}