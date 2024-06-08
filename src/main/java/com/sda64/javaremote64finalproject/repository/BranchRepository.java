package com.sda64.javaremote64finalproject.repository;

import com.sda64.javaremote64finalproject.entity.Branch;
import com.sda64.javaremote64finalproject.enums.EntityStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BranchRepository extends JpaRepository<Branch, Long> {
    Optional<Branch> findByCity(String city);
}
