package com.sda64.javaremote64finalproject.dto;

import com.sda64.javaremote64finalproject.enums.AccountType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CustomerDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String address;
    private Double balance;
    private AccountType accountType;

    //todo
    private String image;
//    private MultipartFile image;
}
