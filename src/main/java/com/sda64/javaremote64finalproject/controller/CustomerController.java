package com.sda64.javaremote64finalproject.controller;

import com.sda64.javaremote64finalproject.dto.AmountDto;
import com.sda64.javaremote64finalproject.dto.CustomerDto;
import com.sda64.javaremote64finalproject.exception.EntityNotFoundException;
import com.sda64.javaremote64finalproject.exception.InvalidBodyException;
import com.sda64.javaremote64finalproject.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/customer")
@CrossOrigin("http://localhost:4200/")
public class CustomerController {
    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/create")
    public ResponseEntity<CustomerDto> createCar(@RequestBody CustomerDto customerDto) {
        CustomerDto createdCustomerDto = customerService.createCustomer(customerDto);
        return new ResponseEntity<>(createdCustomerDto, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerDto> getCustomer(@PathVariable Long id) throws EntityNotFoundException {
        CustomerDto customerFound = customerService.findById(id);
        return new ResponseEntity<>(customerFound, HttpStatus.OK);
    }

    @GetMapping("/userid/{id}")
    public ResponseEntity<CustomerDto> getCustomerByUserId(@PathVariable Long id) {
        CustomerDto customerFound = customerService.findByUserId(id);
        return new ResponseEntity<>(customerFound, HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<CustomerDto> updateCustomer(@RequestBody CustomerDto customerDto) throws EntityNotFoundException {
        CustomerDto customerUpdated = customerService.updateCustomer(customerDto);
        return new ResponseEntity<>(customerUpdated, HttpStatus.OK);
    }

    @GetMapping("/customers")
    public ResponseEntity<List<CustomerDto>> getAllCustomers() {
        return new ResponseEntity<>(customerService.findAll(), HttpStatus.OK);
    }
    @PutMapping("/{id}/addMoney")
    public ResponseEntity<CustomerDto> addMoney(@RequestBody AmountDto amountDto, @PathVariable Long id) throws EntityNotFoundException, InvalidBodyException {
        CustomerDto customerUpdated = customerService.addMoney(id, amountDto);
        return new ResponseEntity<>(customerUpdated, HttpStatus.OK);
    }
    @ExceptionHandler(value = InvalidBodyException.class)
    ResponseEntity<Object> handleIllegalRequests(InvalidBodyException ex) {
        return new ResponseEntity<>("Value is negative", HttpStatus.NOT_ACCEPTABLE);
    }
    @PostMapping("/testupload")
    public ResponseEntity<?> uploadImage(@RequestParam("image")MultipartFile file) throws IOException {
        String uploadImage = customerService.uploadImage(file);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(uploadImage);
    }

    @GetMapping("/testdownload/{firstName}")
    public ResponseEntity<?> downloadImage(@PathVariable String firstName) {
        byte[] imageData = customerService.downloadImage(firstName);
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.valueOf("image/png"))
                .body(imageData);
    }

    @PostMapping("/updateimage/{id}")
    public ResponseEntity<?> updateCustomerImageTEST(@PathVariable Long id, @RequestParam("file") MultipartFile file) throws IOException {
        customerService.updateCustomerImage(id, file);
        return new ResponseEntity<>("DRAGOS IS THE BEST PROGRAMMER", HttpStatus.OK);
    }
}
