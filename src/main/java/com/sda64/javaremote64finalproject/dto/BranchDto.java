package com.sda64.javaremote64finalproject.dto;

import com.sda64.javaremote64finalproject.enums.EntityStatus;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BranchDto {
    private Long id;
    private String city;
    private String address;
    private EntityStatus status;
}
