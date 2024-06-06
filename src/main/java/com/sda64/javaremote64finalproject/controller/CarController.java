package com.sda64.javaremote64finalproject.controller;

import com.sda64.javaremote64finalproject.dto.CarDto;
import com.sda64.javaremote64finalproject.dto.PeriodDto;
import com.sda64.javaremote64finalproject.exception.EntityNotFoundException;
import com.sda64.javaremote64finalproject.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/car")
@CrossOrigin("http://localhost:4200/")
public class CarController {
    private final CarService carService;
    @Autowired
    public CarController(CarService carService) {
        this.carService = carService;
    }

    @PostMapping("/create")
    public ResponseEntity<CarDto> createCar(@RequestBody CarDto carDto) throws EntityNotFoundException {
        CarDto createdCarDto = carService.createCar(carDto);
        return new ResponseEntity<>(createdCarDto, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CarDto> getCar(@PathVariable Long id) throws EntityNotFoundException {
        CarDto carFound = carService.findById(id);
        return new ResponseEntity<>(carFound, HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<CarDto> updateCar(@RequestBody CarDto carDto) throws EntityNotFoundException {
        CarDto carUpdated = carService.updateCar(carDto);
        return new ResponseEntity<>(carUpdated, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCar(@PathVariable Long id) throws EntityNotFoundException {
        CarDto deletedCar = carService.deleteCar(id);
        return new ResponseEntity<>(deletedCar, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<CarDto>> getAllCars() {
        return new ResponseEntity<>(carService.findAll(), HttpStatus.OK);
    }
    @GetMapping("/getByBodyAndColor")
    public ResponseEntity<List<CarDto>> getAllCarsByCarBodyTypeAndColor(@RequestBody CarDto carDto) {
        return new ResponseEntity<>(carService.findAllByCarBodyTypeAndColor(carDto.getCarBodyType(), carDto.getColor()), HttpStatus.OK);
    }
    @GetMapping("/getByBranchId/{branchId}")
    public ResponseEntity<List<CarDto>> getAllByBranchId(@PathVariable Long branchId) {
        return new ResponseEntity<>(carService.getCarsByBranchId(branchId), HttpStatus.OK);
    }
    //best practice "/getallcars" <- nu camel case, se scriu cu litere mici
    //endpointurile mele sunt search by payload ...
    @PostMapping("/availablecarsduringperiod")
    public ResponseEntity<List<CarDto>> getAvailableCars(@RequestBody PeriodDto periodDto) throws EntityNotFoundException {
        return new ResponseEntity<>(carService.getAvailableCars(periodDto), HttpStatus.OK);
    }
}
