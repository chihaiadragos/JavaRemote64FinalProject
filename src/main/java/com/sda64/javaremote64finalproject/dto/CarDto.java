package com.sda64.javaremote64finalproject.dto;

import com.sda64.javaremote64finalproject.enums.CarBodyType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CarDto {
    private Long id;
    private String brand;
    private String model;
    private CarBodyType carBodyType;
    private Integer year;
    private String color;
    private Integer mileage;
    private Integer amount;
    private String imageUrl;
    private BranchDto branch;
}
