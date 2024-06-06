package com.sda64.javaremote64finalproject.entity;

import com.sda64.javaremote64finalproject.enums.AccountType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.File;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
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
    @Lob
    @Column(name = "imagedata", length = 1000)
    private byte[] image;
    @OneToOne
    private User user;
}
