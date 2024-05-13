package com.sda64.javaremote64finalproject.dto;

import com.sda64.javaremote64finalproject.entity.Branch;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ReservationDto {
    private Long id;
    private CustomerDto customer;
    private CarDto car;
    private LocalDate dateFrom;
    private LocalDate dateTo;
    private BranchDto branch;
    private Double amount;
}
