package com.sda64.javaremote64finalproject.repository;

import com.sda64.javaremote64finalproject.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByCustomerId(Long customerId);
    List<Reservation> findByCarId(Long carId);
    List<Reservation> findByBranchId(Long branchId);
}
