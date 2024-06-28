package com.sda64.javaremote64finalproject.dto;

import com.sda64.javaremote64finalproject.enums.AccountType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthenticationDto {
    private Long id;
    private String fullName;
    private AccountType accountType;
    private String token;
}