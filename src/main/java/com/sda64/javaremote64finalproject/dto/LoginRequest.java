package com.sda64.javaremote64finalproject.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginRequest {

    private String email;
    private String password;

}