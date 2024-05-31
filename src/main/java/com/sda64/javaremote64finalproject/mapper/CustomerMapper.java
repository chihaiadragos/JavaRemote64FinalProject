package com.sda64.javaremote64finalproject.mapper;

import com.sda64.javaremote64finalproject.dto.CustomerDto;
import com.sda64.javaremote64finalproject.entity.Customer;
import com.sda64.javaremote64finalproject.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerMapper implements Mapper<Customer, CustomerDto> {
    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerMapper(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public CustomerDto convertToDto(Customer entity) {

        CustomerDto customerDto = new CustomerDto();
        customerDto.setId(entity.getId());
        customerDto.setFirstName(entity.getFirstName());
        customerDto.setLastName(entity.getLastName());
        customerDto.setEmail(entity.getEmail());
        customerDto.setAddress(entity.getAddress());
        customerDto.setImage(entity.getImage());
        customerDto.setBalance(entity.getBalance());

        return customerDto;
    }

    @Override
    public Customer convertToEntity(CustomerDto dto) {

        Customer customer;

        if (dto.getId() != null) {
            customer = customerRepository.findById(dto.getId()).orElse(new Customer());
        } else {
            customer = new Customer();
        }
        customer.setId(dto.getId());
        customer.setFirstName(dto.getFirstName());
        customer.setLastName(dto.getLastName());
        customer.setEmail(dto.getEmail());
        customer.setAddress(dto.getAddress());
        customer.setImage(dto.getImage());
        customer.setBalance(dto.getBalance());

        return customer;
    }
}
