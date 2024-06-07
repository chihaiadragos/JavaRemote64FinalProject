package com.sda64.javaremote64finalproject.service;

import com.sda64.javaremote64finalproject.dto.AuthenticationResponse;
import com.sda64.javaremote64finalproject.dto.LoginRequest;
import com.sda64.javaremote64finalproject.dto.RegisterRequest;
import com.sda64.javaremote64finalproject.entity.Branch;
import com.sda64.javaremote64finalproject.entity.Customer;
import com.sda64.javaremote64finalproject.entity.Employee;
import com.sda64.javaremote64finalproject.entity.User;
import com.sda64.javaremote64finalproject.enums.AccountType;
import com.sda64.javaremote64finalproject.exception.EntityNotFoundException;
import com.sda64.javaremote64finalproject.repository.BranchRepository;
import com.sda64.javaremote64finalproject.repository.CustomerRepository;
import com.sda64.javaremote64finalproject.repository.EmployeeRepository;
import com.sda64.javaremote64finalproject.repository.UserRepository;
import com.sda64.javaremote64finalproject.util.ImageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@EnableCaching
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final CustomerRepository customerRepository;
    private final BranchRepository branchRepository;
    private final EmployeeRepository employeeRepository;

    public AuthenticationResponse register(RegisterRequest request) throws EntityNotFoundException, IOException {
        var user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .accountType(request.getAccountType())
                .build();
        user = userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);

        if (request.getAccountType().equals(AccountType.CLIENT)) {
            Customer customer = new Customer();
            customer.setFirstName(request.getFirstName());
            customer.setLastName(request.getLastName());
            customer.setAddress(request.getAddress());
            customer.setEmail(request.getEmail());
            customer.setAccountType(AccountType.CLIENT);
            customer.setBalance(0d);
            customer.setUser(user);

            ClassPathResource imgFile = new ClassPathResource("static/idefes.png");
            byte[] imageBytes = Files.readAllBytes(imgFile.getFile().toPath());
            customer.setImage(ImageUtils.compressImage(imageBytes));
            customerRepository.save(customer);
        } else {
            Employee employee = new Employee();
            employee.setFirstName(request.getFirstName());
            employee.setLastName(request.getLastName());
            employee.setUser(user);
            Optional<Branch> branchOptional = branchRepository.findByCity(request.getBranch());
            if (branchOptional.isPresent()) {
                employee.setBranch(branchOptional.get());
            } else {
                throw new EntityNotFoundException(String.format("Branch from city %s not found", request.getBranch()));
            }
            employeeRepository.save(employee);
        }

        return AuthenticationResponse.builder().token(jwtToken).build();
    }

    public AuthenticationResponse authenticate(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = userRepository.findByEmail(request.getEmail()).orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .accountType(user.getAccountType())
                .id(user.getId())
                .fullName(user.getFullName())
                .token(jwtToken)
                .build();
    }
}