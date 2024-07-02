package com.sda64.javaremote64finalproject.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sda64.javaremote64finalproject.enums.CarBodyType;
import com.sda64.javaremote64finalproject.enums.EntityStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    private String brand;

    private String model;

    @Enumerated(EnumType.STRING)
    private CarBodyType carBodyType;

    private Integer year;

    private String color;

    private Integer mileage;

    private Integer amount;

    @Enumerated(EnumType.STRING)
    private EntityStatus status;

    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "branch_id")
    @JsonIgnore
    private Branch branch;

    @OneToMany(mappedBy = "car")
    @JsonIgnore
    private List<Reservation> reservations;
}
