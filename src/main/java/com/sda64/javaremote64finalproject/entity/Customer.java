package com.sda64.javaremote64finalproject.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Long id;

    private String firstName;
    private String lastName;
    private String email;
    private String address;
    private Double balance;
    @OneToMany(mappedBy = "customer")
    private List<Reservation> reservations;
}
