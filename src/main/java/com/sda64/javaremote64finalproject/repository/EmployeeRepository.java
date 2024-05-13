package com.sda64.javaremote64finalproject.repository;

import com.sda64.javaremote64finalproject.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    List<Employee> findAllByBranchId(Long branchId);
}
