package com.sda64.javaremote64finalproject.controller;

import com.sda64.javaremote64finalproject.dto.CarDto;
import com.sda64.javaremote64finalproject.dto.EmployeeDto;
import com.sda64.javaremote64finalproject.dto.PeriodDto;
import com.sda64.javaremote64finalproject.dto.ReservationDto;
import com.sda64.javaremote64finalproject.entity.Reservation;
import com.sda64.javaremote64finalproject.exception.EntityNotFoundException;
import com.sda64.javaremote64finalproject.exception.InsuficientFoundsException;
import com.sda64.javaremote64finalproject.exception.InvalidBodyException;
import com.sda64.javaremote64finalproject.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservation")
@CrossOrigin("http://localhost:4200/")
public class ReservationController {
    private final ReservationService reservationService;
    @Autowired
    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping("/create")
    public ResponseEntity<ReservationDto> createReservation(@RequestBody ReservationDto reservationDto) throws EntityNotFoundException, InsuficientFoundsException {
        ReservationDto createdReservationDto = reservationService.createReservation(reservationDto);
        return new ResponseEntity<>(createdReservationDto, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservationDto> getReservation(@PathVariable Long id) throws EntityNotFoundException {
        ReservationDto reservationFound = reservationService.findById(id);
        return new ResponseEntity<>(reservationFound, HttpStatus.FOUND);
    }

    @PutMapping("/update")
    public ResponseEntity<ReservationDto> updateReservation(@RequestBody ReservationDto reservationDto) throws EntityNotFoundException {
        ReservationDto reservationUpdated = reservationService.updateReservation(reservationDto);
        return new ResponseEntity<>(reservationUpdated, HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteReservation(@PathVariable Long id) throws EntityNotFoundException {
        ReservationDto deletedReservation = reservationService.deleteReservation(id);
        return new ResponseEntity<>(deletedReservation, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<ReservationDto>> getAllReservations() {
        return new ResponseEntity<>(reservationService.findAll(), HttpStatus.OK);
    }
    @GetMapping("/getByCustomerId/{customerId}")
    public ResponseEntity<List<ReservationDto>> getAllByCustomerId(@PathVariable Long customerId) {
        return new ResponseEntity<>(reservationService.getReservationsByCustomerId(customerId), HttpStatus.OK);
    }
    @GetMapping("/getByCarId/{carId}")
    public ResponseEntity<List<ReservationDto>> getAllByCarId(@PathVariable Long carId) {
        return new ResponseEntity<>(reservationService.getReservationsByCarId(carId), HttpStatus.OK);
    }
    @GetMapping("/getByBranchId/{branchId}")
    public ResponseEntity<List<ReservationDto>> getAllByBranchId(@PathVariable Long branchId) {
        return new ResponseEntity<>(reservationService.getReservationsByBranchId(branchId), HttpStatus.OK);
    }

    @ExceptionHandler(value = InsuficientFoundsException.class)
    ResponseEntity<Object> handleIllegalRequests() {
        return new ResponseEntity<>("Insuficient founds", HttpStatus.NOT_ACCEPTABLE);
    }

}
