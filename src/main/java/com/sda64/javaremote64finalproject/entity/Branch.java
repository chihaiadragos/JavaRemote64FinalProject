package com.sda64.javaremote64finalproject.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Branch {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Long id;
    private String city;
    private String address;

    @OneToMany(mappedBy = "branch")
    @JsonIgnore
    private List<Car> cars;

    @OneToMany(mappedBy = "branch")
    @JsonIgnore
    private List<Employee> employees;

    @OneToMany(mappedBy = "branch")
    @JsonIgnore
    private List<Reservation> reservations;
}
