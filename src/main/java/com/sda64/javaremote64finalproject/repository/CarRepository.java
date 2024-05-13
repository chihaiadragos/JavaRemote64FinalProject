package com.sda64.javaremote64finalproject.repository;

import com.sda64.javaremote64finalproject.entity.Car;
import com.sda64.javaremote64finalproject.entity.Reservation;
import com.sda64.javaremote64finalproject.enums.CarBodyType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CarRepository extends JpaRepository<Car, Long> {

    List<Car> findAllByCarBodyTypeAndColor(CarBodyType carBodyType, String color);

    List<Car> findByBranchId(Long branchId);

}
