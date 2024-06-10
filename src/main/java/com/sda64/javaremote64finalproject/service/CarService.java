package com.sda64.javaremote64finalproject.service;

import com.sda64.javaremote64finalproject.dto.CarDto;
import com.sda64.javaremote64finalproject.dto.PeriodDto;
import com.sda64.javaremote64finalproject.entity.Branch;
import com.sda64.javaremote64finalproject.entity.Car;
import com.sda64.javaremote64finalproject.entity.Reservation;
import com.sda64.javaremote64finalproject.enums.CarBodyType;
import com.sda64.javaremote64finalproject.enums.EntityStatus;
import com.sda64.javaremote64finalproject.exception.EntityNotFoundException;
import com.sda64.javaremote64finalproject.mapper.CarMapper;
import com.sda64.javaremote64finalproject.repository.BranchRepository;
import com.sda64.javaremote64finalproject.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CarService {
    private final BranchRepository branchRepository;
    private final CarRepository carRepository;
    private final CarMapper carMapper;
    private final ReservationService reservationService;

    @Autowired
    public CarService(BranchRepository branchRepository, CarRepository carRepository, CarMapper carMapper, ReservationService reservationService) {
        this.branchRepository = branchRepository;
        this.carRepository = carRepository;
        this.carMapper = carMapper;
        this.reservationService = reservationService;
    }

    public CarDto createCar(CarDto carDto) throws EntityNotFoundException {
        Car car = carMapper.convertToEntity(carDto);
        return carMapper.convertToDto(carRepository.save(car));
    }

    public CarDto findById(Long id) throws EntityNotFoundException {
        Car entityCar = carRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(String.format("Car with %s does not exist", id)));
        return carMapper.convertToDto(entityCar);
    }

    public CarDto updateCar(CarDto carDto) throws EntityNotFoundException {
        Car entityCar = carRepository
                .findById(carDto.getId())
                .orElseThrow(() -> new EntityNotFoundException(String.format("Car with %s does not exist", carDto.getId())));


        if (carDto.getModel() != null && !entityCar.getModel().equalsIgnoreCase(carDto.getModel())) {
            entityCar.setModel(carDto.getModel());
        } else {
            entityCar.setModel("unknown");
        }
        if (carDto.getCarBodyType() != null && entityCar.getCarBodyType().compareTo(carDto.getCarBodyType()) != 0) {
            entityCar.setCarBodyType(carDto.getCarBodyType());
        }
        if (carDto.getYear() != null && !entityCar.getYear().equals(carDto.getYear())) {
            entityCar.setYear(carDto.getYear());
        }
        if (carDto.getColor() != null && !entityCar.getColor().equalsIgnoreCase(carDto.getColor())) {
            entityCar.setColor(carDto.getColor());
        }
        if (carDto.getMileage() != null && !entityCar.getMileage().equals(carDto.getMileage())) {
            entityCar.setMileage(carDto.getMileage());
        }
        if (carDto.getAmount() != null && !entityCar.getAmount().equals(carDto.getAmount())) {
            entityCar.setAmount(carDto.getAmount());
        }
        if (carDto.getImageUrl() != null && !entityCar.getImageUrl().equals(carDto.getImageUrl())) {
            entityCar.setImageUrl(carDto.getImageUrl());
        }

        if (carDto.getStatus() != null && !entityCar.getStatus().equals(carDto.getStatus())) {
            entityCar.setStatus(carDto.getStatus());
        }

        if (carDto.getBranch() != null && !entityCar.getBranch().getId().equals(carDto.getBranch().getId())) {
            Optional<Branch> branch = branchRepository.findById(carDto.getBranch().getId());
            if (branch.isPresent()) {
                entityCar.setBranch(branch.get());
            } else {
                throw new EntityNotFoundException(String.format("Branch with id %s does not exist", carDto.getBranch().getId()));
            }
        }


        return carMapper.convertToDto(carRepository.save(entityCar));
    }

    public CarDto deleteCar(Long id) throws EntityNotFoundException {
        Car entityCar = carRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(String.format("Car with %s does not exist", id)));
        carRepository.deleteById(id);
        return carMapper.convertToDto(entityCar);
    }

    public List<CarDto> findAll() {
        List<Car> carList = carRepository.findAll();
        List<CarDto> carDtoList = new ArrayList<>();
        for (Car car : carList) {
            carDtoList.add(carMapper.convertToDto(car));
        }
        return carDtoList;
    }

    public List<CarDto> findAllAvailable() {
        List<Car> carList = carRepository.findAll();
        List<CarDto> carDtoList = new ArrayList<>();

        List<Car> availableCars = carList.stream()
                .filter(car -> car.getStatus() == EntityStatus.AVAILABLE)
                .toList();

        for (Car car : availableCars) {
            carDtoList.add(carMapper.convertToDto(car));
        }
        return carDtoList;
    }
    public List<CarDto> findAllByCarBodyTypeAndColor(CarBodyType carBodyType, String color) {
        List<Car> carList = carRepository.findAllByCarBodyTypeAndColor(carBodyType, color);
        List<CarDto> carDtoList = new ArrayList<>();
        for (Car car : carList) {
            carDtoList.add(carMapper.convertToDto(car));
        }
        return carDtoList;
    }
    public List<CarDto> getCarsByBranchId(Long branchId) {
        List<Car> carList = carRepository.findByBranchId(branchId);
        List<CarDto> carDtoList = new ArrayList<>();
        for (Car car : carList) {
            carDtoList.add(carMapper.convertToDto(car));
        }
        return carDtoList;
    }
    public List<CarDto> getAvailableCars(PeriodDto periodDto) {
        List<CarDto> carDtoList = findAllAvailable();
        Set<CarDto> availableCars = new HashSet<>(carDtoList);

        List<Reservation> reservationListFromPeriod = reservationService.findAllByPeriod(periodDto);

        var newCars = availableCars.stream().filter(car -> !reservationListFromPeriod
                .stream()
                .anyMatch(r -> Objects.equals(r.getCar().getId(), car.getId()))).toList();
        return newCars;
    }
}
