package com.sda64.javaremote64finalproject.mapper;

import com.sda64.javaremote64finalproject.dto.BranchDto;
import com.sda64.javaremote64finalproject.dto.CarDto;
import com.sda64.javaremote64finalproject.dto.CustomerDto;
import com.sda64.javaremote64finalproject.dto.ReservationDto;
import com.sda64.javaremote64finalproject.entity.Branch;
import com.sda64.javaremote64finalproject.entity.Car;
import com.sda64.javaremote64finalproject.entity.Customer;
import com.sda64.javaremote64finalproject.entity.Reservation;
import com.sda64.javaremote64finalproject.enums.ReservationStatus;
import com.sda64.javaremote64finalproject.exception.EntityNotFoundException;
import com.sda64.javaremote64finalproject.repository.BranchRepository;
import com.sda64.javaremote64finalproject.repository.CarRepository;
import com.sda64.javaremote64finalproject.repository.CustomerRepository;
import com.sda64.javaremote64finalproject.repository.ReservationRepository;
import com.sda64.javaremote64finalproject.service.BranchService;
import com.sda64.javaremote64finalproject.service.CarService;
import com.sda64.javaremote64finalproject.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ReservationMapper implements Mapper<Reservation, ReservationDto> {
    private final ReservationRepository reservationRepository;
    private final CustomerMapper customerMapper;
    private final CarMapper carMapper;
    private final BranchMapper branchMapper;
    private final CustomerRepository customerRepository;
    private final CarRepository carRepository;
    private final BranchRepository branchRepository;

    @Autowired
    public ReservationMapper(ReservationRepository reservationRepository, CustomerMapper customerMapper, CarMapper carMapper, BranchMapper branchMapper, CustomerRepository customerRepository, CarRepository carRepository, BranchRepository branchRepository) {
        this.reservationRepository = reservationRepository;
        this.customerMapper = customerMapper;
        this.carMapper = carMapper;
        this.branchMapper = branchMapper;
        this.customerRepository = customerRepository;
        this.carRepository = carRepository;
        this.branchRepository = branchRepository;
    }

    @Override
    public ReservationDto convertToDto(Reservation entity) {
        ReservationDto reservationDto = new ReservationDto();
        reservationDto.setId(entity.getId());
        reservationDto.setCustomer(customerMapper.convertToDto(entity.getCustomer()));
        reservationDto.setCar(carMapper.convertToDto(entity.getCar()));
        reservationDto.setDateFrom(entity.getDateFrom());
        reservationDto.setDateTo(entity.getDateTo());
        reservationDto.setBranch(branchMapper.convertToDto(entity.getBranch()));
//        reservationDto.setBranchReturn(branchMapper.convertToDto(entity.getBranchReturn()));
        reservationDto.setAmount(entity.getAmount());
        reservationDto.setReservationStatus(entity.getReservationStatus());
        return reservationDto;
    }

    @Override
    public Reservation convertToEntity(ReservationDto dto) throws EntityNotFoundException {

        Reservation reservation;

        if (dto.getId() != null && dto.getId() >= 0) {
            reservation = reservationRepository.findById(dto.getId()).orElse(new Reservation());
        } else {
            reservation = new Reservation();
        }
        reservation.setId(dto.getId());
        if (dto.getCustomer().getId() != null) {
            Optional<Customer> customerDto = customerRepository.findById(dto.getCustomer().getId());
            if (customerDto.isPresent()) {
                reservation.setCustomer(customerDto.get());
            } else {
                throw new EntityNotFoundException(String.format("Customer with id %s not found", dto.getCustomer().getId()));
            }
        }
        if (dto.getCar().getId() != null) {
            Optional<Car> carDto = carRepository.findById(dto.getCar().getId());
            if (carDto.isPresent()) {
                reservation.setCar(carDto.get());
            } else {
                throw new EntityNotFoundException(String.format("Car with id %s not found", dto.getCar().getId()));
            }
        }
        reservation.setDateFrom(dto.getDateFrom());
        reservation.setDateTo(dto.getDateTo());
        if (dto.getBranch().getId() != null) {
            Optional<Branch> branchDto = branchRepository.findById(dto.getBranch().getId());
            if (branchDto.isPresent()) {
                reservation.setBranch(branchDto.get());
            } else {
                throw new EntityNotFoundException(String.format("Branch with id %s not found", dto.getBranch().getId()));
            }
        }
//        if (dto.getBranchReturn().getId() != null) {
//            Optional<Branch> branchDto = branchRepository.findById(dto.getBranchReturn().getId());
//            if (branchDto.isPresent()) {
//                reservation.setBranchReturn(branchDto.get());
//            } else {
//                throw new EntityNotFoundException(String.format("Branch with id %s not found", dto.getBranchReturn().getId()));
//            }
//        }
        reservation.setAmount(dto.getAmount());
        if (dto.getReservationStatus() == null) {
            reservation.setReservationStatus(ReservationStatus.PENDING);
        }
        return reservation;
    }
}
