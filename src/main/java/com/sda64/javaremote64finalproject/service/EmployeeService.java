package com.sda64.javaremote64finalproject.service;

import com.sda64.javaremote64finalproject.dto.EmployeeDto;
import com.sda64.javaremote64finalproject.entity.Employee;
import com.sda64.javaremote64finalproject.exception.EntityNotFoundException;
import com.sda64.javaremote64finalproject.mapper.EmployeeMapper;
import com.sda64.javaremote64finalproject.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository, EmployeeMapper employeeMapper) {
        this.employeeRepository = employeeRepository;
        this.employeeMapper = employeeMapper;
    }

    public EmployeeDto createEmployee(EmployeeDto employeeDto) throws EntityNotFoundException {
        Employee employee = employeeMapper.convertToEntity(employeeDto);
        return employeeMapper.convertToDto(employeeRepository.save(employee));
    }

    public EmployeeDto findById(Long id) throws EntityNotFoundException {
        Employee entityEmployee = employeeRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(String.format("Employee with %s does not exist", id)));
        return employeeMapper.convertToDto(entityEmployee);
    }

    public EmployeeDto updateEmployee(EmployeeDto employeeDto) throws EntityNotFoundException {
        Employee entityEmployee = employeeRepository
                .findById(employeeDto.getId())
                .orElseThrow(() -> new EntityNotFoundException(String.format("Employee with %s does not exist", employeeDto.getId())));
        if (employeeDto.getFirstName() != null && !entityEmployee.getFirstName().equals(employeeDto.getFirstName())) {
            entityEmployee.setFirstName(employeeDto.getFirstName());
        }
        if (employeeDto.getLastName() != null && !entityEmployee.getLastName().equals(employeeDto.getLastName())) {
            entityEmployee.setLastName(employeeDto.getLastName());
        }

        return employeeMapper.convertToDto(employeeRepository.save(entityEmployee));
    }

    public EmployeeDto deleteEmployee(Long id) throws EntityNotFoundException {
        Employee entityEmployee = employeeRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(String.format("Employee with %s does not exist", id)));
        employeeRepository.deleteById(id);
        return employeeMapper.convertToDto(entityEmployee);
    }

    public List<EmployeeDto> findAll() {
        List<Employee> employeeList =  employeeRepository.findAll();
        List<EmployeeDto> employeeDtoList = new ArrayList<>();
        for (Employee employee : employeeList) {
            employeeDtoList.add(employeeMapper.convertToDto(employee));
        }
        return employeeDtoList;
    }
    public List<EmployeeDto> findAllByBranchId(Long branchId) {
        List<Employee> employeeList =  employeeRepository.findAllByBranchId(branchId);
        List<EmployeeDto> employeeDtoList = new ArrayList<>();
        for (Employee employee : employeeList) {
            employeeDtoList.add(employeeMapper.convertToDto(employee));
        }
        return employeeDtoList;
    }
}
