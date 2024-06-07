package com.sda64.javaremote64finalproject.mapper;

import com.sda64.javaremote64finalproject.dto.BranchDto;
import com.sda64.javaremote64finalproject.dto.EmployeeDto;
import com.sda64.javaremote64finalproject.entity.Employee;
import com.sda64.javaremote64finalproject.exception.EntityNotFoundException;
import com.sda64.javaremote64finalproject.repository.EmployeeRepository;
import com.sda64.javaremote64finalproject.service.BranchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeMapper implements Mapper<Employee, EmployeeDto> {
    private final EmployeeRepository employeeRepository;
    private final BranchMapper branchMapper;
    private final BranchService branchService;

    @Autowired
    public EmployeeMapper(EmployeeRepository employeeRepository, BranchMapper branchMapper, BranchService branchService) {
        this.employeeRepository = employeeRepository;
        this.branchMapper = branchMapper;
        this.branchService = branchService;
    }

    @Override
    public EmployeeDto convertToDto(Employee entity) {
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setId(entity.getId());
        employeeDto.setFirstName(entity.getFirstName());
        employeeDto.setLastName(entity.getLastName());
        employeeDto.setEmail(employeeDto.getEmail());
        employeeDto.setBranch(branchMapper.convertToDto(entity.getBranch()));

        return employeeDto;
    }

    @Override
    public Employee convertToEntity(EmployeeDto dto) throws EntityNotFoundException {

        Employee employee;

        if (dto.getId() != null) {
            employee = employeeRepository.findById(dto.getId()).orElse(new Employee());
        } else {
            employee = new Employee();
        }
        employee.setId(dto.getId());
        employee.setFirstName(dto.getFirstName());
        employee.setLastName(dto.getLastName());
        employee.setEmail(dto.getEmail());
        if (dto.getBranch().getId() != null) {
            BranchDto branchDto = branchService.findById(dto.getBranch().getId());
            employee.setBranch(branchMapper.convertToEntity(branchDto));
        }

        return employee;
    }
}
