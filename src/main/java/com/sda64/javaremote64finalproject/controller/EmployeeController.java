package com.sda64.javaremote64finalproject.controller;

import com.sda64.javaremote64finalproject.dto.EmployeeDto;
import com.sda64.javaremote64finalproject.exception.EntityNotFoundException;
import com.sda64.javaremote64finalproject.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employee")
@CrossOrigin("http://localhost:4200/")
public class EmployeeController {
    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping("/create")
    public ResponseEntity<EmployeeDto> createEmployee(@RequestBody EmployeeDto employeeDto) throws EntityNotFoundException {
        EmployeeDto createdEmployeeDto = employeeService.createEmployee(employeeDto);
        return new ResponseEntity<>(createdEmployeeDto, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDto> getEmployee(@PathVariable Long id) throws EntityNotFoundException {
        EmployeeDto employeeFound = employeeService.findById(id);
        return new ResponseEntity<>(employeeFound, HttpStatus.FOUND);
    }

    @PutMapping("/update")
    public ResponseEntity<EmployeeDto> updateEmployee(@RequestBody EmployeeDto employeeDto) throws EntityNotFoundException {
        EmployeeDto employeeUpdated = employeeService.updateEmployee(employeeDto);
        return new ResponseEntity<>(employeeUpdated, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable Long id) throws EntityNotFoundException {
        EmployeeDto deletedEmployee = employeeService.deleteEmployee(id);
        return new ResponseEntity<>(deletedEmployee, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<EmployeeDto>> getAllEmployees() {
        return new ResponseEntity<>(employeeService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/getByBranchId/{branchId}")
    public ResponseEntity<List<EmployeeDto>> getAllByBranchId(@PathVariable Long branchId) {
        return new ResponseEntity<>(employeeService.findAllByBranchId(branchId), HttpStatus.OK);
    }
}
