package com.sda64.javaremote64finalproject.dto;

import com.sda64.javaremote64finalproject.enums.AccountType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserDto {
    private String email;
    private String password;
    private AccountType accountType;
}
