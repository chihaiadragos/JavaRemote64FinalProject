package com.sda64.javaremote64finalproject.dto;

import com.sda64.javaremote64finalproject.enums.AccountType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    private String firstName;
    private String lastName;
    private String address;
    private String email;
    private String password;
//    private String image;
    private String branch;
    private AccountType accountType;
}