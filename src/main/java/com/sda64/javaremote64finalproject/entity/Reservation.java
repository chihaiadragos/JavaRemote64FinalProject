package com.sda64.javaremote64finalproject.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sda64.javaremote64finalproject.enums.ReservationStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    @JsonIgnore
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "car_id")
    @JsonIgnore
    private Car car;

    private LocalDate dateFrom;

    private LocalDate dateTo;

    @ManyToOne
    @JoinColumn(name = "branch_id")
    @JsonIgnore
    private Branch branch;


    private Double amount;
    @Column(name = "reservation_status")
    @Enumerated(EnumType.STRING)
    private ReservationStatus reservationStatus;

}
