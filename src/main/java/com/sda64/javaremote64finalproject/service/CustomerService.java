package com.sda64.javaremote64finalproject.service;

import com.sda64.javaremote64finalproject.dto.AmountDto;
import com.sda64.javaremote64finalproject.dto.CustomerDto;
import com.sda64.javaremote64finalproject.entity.Customer;
import com.sda64.javaremote64finalproject.exception.EntityNotFoundException;
import com.sda64.javaremote64finalproject.exception.InvalidBodyException;
import com.sda64.javaremote64finalproject.mapper.CustomerMapper;
import com.sda64.javaremote64finalproject.repository.CustomerRepository;
import com.sda64.javaremote64finalproject.util.ImageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    @Autowired
    public CustomerService(CustomerRepository customerRepository, CustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
    }

    public CustomerDto createCustomer(CustomerDto customerDto) {
        Customer customer = customerMapper.convertToEntity(customerDto);
        return customerMapper.convertToDto(customerRepository.save(customer));
    }

    public CustomerDto findById(Long id) throws EntityNotFoundException {
        Customer entityCustomer = customerRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Customer with %s does not exist", id)));
        return customerMapper.convertToDto(entityCustomer);
    }

    public CustomerDto findByUserId(Long id) {
        Customer entityCustomer = customerRepository.findByUserId(id);
        return customerMapper.convertToDto(entityCustomer);
    }

    public CustomerDto updateCustomer(CustomerDto customerDto) throws EntityNotFoundException {
        Customer entityCustomer = customerRepository
                .findById(customerDto.getId())
                .orElseThrow(() -> new EntityNotFoundException(String.format("Customer with %s does not exist", customerDto.getId())));
        if (customerDto.getFirstName() != null && !entityCustomer.getFirstName().equals(customerDto.getFirstName())) {
            entityCustomer.setFirstName(customerDto.getFirstName());
        }
        if (customerDto.getLastName() != null && !entityCustomer.getLastName().equals(customerDto.getLastName())) {
            entityCustomer.setLastName(customerDto.getLastName());
        }
        if (customerDto.getEmail() != null && !entityCustomer.getEmail().equals(customerDto.getEmail())) {
            entityCustomer.setEmail(customerDto.getEmail());
        }
        if (customerDto.getAddress() != null && !entityCustomer.getAddress().equals(customerDto.getAddress())) {
            entityCustomer.setAddress(customerDto.getAddress());
        }
        if (customerDto.getImage() != null && !entityCustomer.getImage().equals(customerDto.getImage())) {
            entityCustomer.setImage(customerDto.getImage().getBytes());
        }
        if (customerDto.getBalance() != null && !entityCustomer.getBalance().equals(customerDto.getBalance())) {
            entityCustomer.setBalance(customerDto.getBalance());
        }

        return customerMapper.convertToDto(customerRepository.save(entityCustomer));
    }

    public List<CustomerDto> findAll() {
        List<Customer> customerList = customerRepository.findAll();
        List<CustomerDto> customerDtoList = new ArrayList<>();
        for (Customer customer : customerList) {
            customerDtoList.add(customerMapper.convertToDto(customer));
        }
        return customerDtoList;
    }

    public CustomerDto addMoney(Long id, AmountDto amount) throws EntityNotFoundException, InvalidBodyException {
        //validare pe amount sa fie pozitiv -> exceptie
        if (amount.getAmount() < 0) {
            throw new InvalidBodyException(String.format("Value can not be negative: your value = %s", amount.getAmount()));
        }
        Customer entityCustomer = customerRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Customer with %s does not exist", id)));
        entityCustomer.setBalance(entityCustomer.getBalance() + amount.getAmount());
        return customerMapper.convertToDto(customerRepository.save(entityCustomer));
    }


    public String uploadImage(MultipartFile file) throws IOException {
        Customer customer = customerRepository.save(Customer.builder()
                .firstName(file.getOriginalFilename())
                .email(file.getContentType())
                .image(ImageUtils.compressImage(file.getBytes()))
                .build());

        if (customer != null) {
            return "file uploaded successfully: " + file.getOriginalFilename();
        }
        return null;
    }

    public byte[] downloadImage(String firstName) {
        Optional<Customer> dbCustomer = customerRepository.findByFirstName(firstName);
        byte[] images = ImageUtils.decompressImage(dbCustomer.get().getImage());
        return images;
    }

    public String updateCustomerImage(Long id, MultipartFile file) throws IOException {
        Customer customer = customerRepository
                .findByUserId(id);

        customer.setImage(ImageUtils.decompressImage(file.getBytes()));
        customerRepository.save(customer);
        return "customer updated";
    }
}
