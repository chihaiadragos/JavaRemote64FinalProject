package com.sda64.javaremote64finalproject.repository;

import com.sda64.javaremote64finalproject.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Customer findByUserId(Long id);

    Optional<Customer> findByFirstName(String firstName);
}
