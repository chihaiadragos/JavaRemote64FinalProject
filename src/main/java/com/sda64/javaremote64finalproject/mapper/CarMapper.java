package com.sda64.javaremote64finalproject.mapper;

import com.sda64.javaremote64finalproject.dto.BranchDto;
import com.sda64.javaremote64finalproject.dto.CarDto;
import com.sda64.javaremote64finalproject.entity.Car;
import com.sda64.javaremote64finalproject.enums.CarBodyType;
import com.sda64.javaremote64finalproject.enums.EntityStatus;
import com.sda64.javaremote64finalproject.exception.EntityNotFoundException;
import com.sda64.javaremote64finalproject.repository.CarRepository;
import com.sda64.javaremote64finalproject.service.BranchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CarMapper implements Mapper<Car, CarDto>{
    private final CarRepository carRepository;
    private final BranchMapper branchMapper;
    private final BranchService branchService;
    @Autowired
    public CarMapper(CarRepository carRepository, BranchMapper branchMapper, BranchService branchService) {
        this.carRepository = carRepository;
        this.branchMapper = branchMapper;
        this.branchService = branchService;
    }

    @Override
    public CarDto convertToDto(Car entity) {

        CarDto carDto = new CarDto();
        carDto.setId(entity.getId());
        carDto.setBrand(entity.getBrand());
        carDto.setModel(entity.getModel());
        carDto.setCarBodyType(entity.getCarBodyType());
        carDto.setYear(entity.getYear());
        carDto.setColor(entity.getColor());
        carDto.setMileage(entity.getMileage());
        carDto.setAmount(entity.getAmount());
        carDto.setImageUrl(entity.getImageUrl());
        carDto.setStatus(entity.getStatus());
        carDto.setBranch(branchMapper.convertToDto(entity.getBranch()));

        return carDto;
    }

    @Override
    public Car convertToEntity(CarDto dto) throws EntityNotFoundException {

        Car car;

        if (dto.getId() != null && dto.getId() >= 0) {
            car = carRepository.findById(dto.getId()).orElse(new Car());
        } else {
            car = new Car();
        }
        car.setBrand(dto.getBrand());
        car.setModel(dto.getModel());
        if (dto.getCarBodyType() == null) {
            car.setCarBodyType(CarBodyType.SEDAN);
        } else {
            car.setCarBodyType(dto.getCarBodyType());
        }
        car.setYear(dto.getYear());
        car.setColor(dto.getColor());
        car.setMileage(dto.getMileage());
        car.setAmount(dto.getAmount());
        if (dto.getStatus() == null) {
            car.setStatus(EntityStatus.AVAILABLE);
        }
        car.setImageUrl(dto.getImageUrl());
        if (dto.getBranch().getId() != null) {
            BranchDto branchDto = branchService.findById(dto.getBranch().getId());
            car.setBranch(branchMapper.convertToEntity(branchDto));
        }

        return car;
    }
}
