package com.sda64.javaremote64finalproject.service;

import com.sda64.javaremote64finalproject.dto.PeriodDto;
import com.sda64.javaremote64finalproject.dto.ReservationDto;
import com.sda64.javaremote64finalproject.entity.Car;
import com.sda64.javaremote64finalproject.entity.Customer;
import com.sda64.javaremote64finalproject.entity.Reservation;
import com.sda64.javaremote64finalproject.enums.ReservationStatus;
import com.sda64.javaremote64finalproject.exception.EntityNotFoundException;
import com.sda64.javaremote64finalproject.exception.InsuficientFoundsException;
import com.sda64.javaremote64finalproject.mapper.ReservationMapper;
import com.sda64.javaremote64finalproject.repository.CarRepository;
import com.sda64.javaremote64finalproject.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final ReservationMapper reservationMapper;
    private final CarRepository carRepository;

    @Autowired
    public ReservationService(ReservationRepository reservationRepository, ReservationMapper reservationMapper, CarRepository carRepository) {
        this.reservationRepository = reservationRepository;
        this.reservationMapper = reservationMapper;
        this.carRepository = carRepository;
    }

    public Boolean checkFoundsReservation(Reservation reservation) {
        Double reservationTotal = reservation.getAmount();
        Customer customer = reservation.getCustomer();
        return !((customer.getBalance() - reservationTotal) < 0);
    }

    public ReservationDto createReservation(ReservationDto reservationDto) throws EntityNotFoundException, InsuficientFoundsException {
        Reservation reservation = reservationMapper.convertToEntity(reservationDto);

        Double reservationTotal = reservationDto.getAmount();
        Customer customer = reservation.getCustomer();
        //sa imi fac o metoda checkreservation
        // care sami verificce ca are bani sau nu -> boolean pe care sal
        // trimit in front end -> true : create reservation
        // / false notificare ca nu poate face rezervarea
        if ((customer.getBalance() - reservationTotal) < 0) {
            throw new InsuficientFoundsException(String.format("Insuficient founds: your balance is %s", customer.getBalance()));
        }
        customer.setBalance(customer.getBalance() - reservationTotal);
        reservation.setReservationStatus(ReservationStatus.PENDING);

        return reservationMapper.convertToDto(reservationRepository.save(reservation));
    }

    public ReservationDto findById(Long id) throws EntityNotFoundException {
        Reservation entityReservation = reservationRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Reservation with %s does not exist", id)));
        return reservationMapper.convertToDto(entityReservation);
    }

    public ReservationDto updateReservation(ReservationDto reservationDto) throws EntityNotFoundException {
        Reservation entityReservation = reservationRepository
                .findById(reservationDto.getId())
                .orElseThrow(() -> new EntityNotFoundException(String.format("Reservation with %s does not exist", reservationDto.getId())));
        if (reservationDto.getCar() != null && !entityReservation.getCar().getId().equals(reservationDto.getCar().getId())) {
            Optional<Car> car = carRepository.findById(reservationDto.getCar().getId());
            if (car.isPresent()) {
                entityReservation.setCar(car.get());
            } else {
                throw new EntityNotFoundException(String.format("Car with id %s does not exist", reservationDto.getCar().getId()));
            }
        }
        if (reservationDto.getDateFrom() != null && !entityReservation.getDateFrom().equals(reservationDto.getDateFrom())) {
            entityReservation.setDateFrom(reservationDto.getDateFrom());
        }
        if (reservationDto.getDateTo() != null && !entityReservation.getDateTo().equals(reservationDto.getDateTo())) {
            entityReservation.setDateTo(reservationDto.getDateTo());
        }
//        if (reservationDto.getReservationStatus() != null && !entityReservation.getReservationStatus().equals(reservationDto.getReservationStatus())) {
//            entityReservation.setReservationStatus(reservationDto.getReservationStatus());
//        }
        entityReservation.setReservationStatus(reservationDto.getReservationStatus());
        return reservationMapper.convertToDto(reservationRepository.save(entityReservation));
    }

    public ReservationDto deleteReservation(Long id) throws EntityNotFoundException {
        Reservation entityReservation = reservationRepository
                .findById(id).orElseThrow(() -> new EntityNotFoundException(String.format("Reservation with %s does not exist", id)));
        reservationRepository.deleteById(id);
        return reservationMapper.convertToDto(entityReservation);
    }

    public List<ReservationDto> findAll() {
        List<Reservation> reservationList = reservationRepository.findAll();
        List<ReservationDto> reservationDtoList = new ArrayList<>();
        for (Reservation reservation : reservationList) {
            reservationDtoList.add(reservationMapper.convertToDto(reservation));
        }
        return reservationDtoList;
    }

    public List<ReservationDto> getReservationsByCustomerId(Long customerId) {
        List<Reservation> reservationList = reservationRepository.findByCustomerId(customerId);
        List<ReservationDto> reservationDtoList = new ArrayList<>();
        for (Reservation reservation : reservationList) {
            reservationDtoList.add(reservationMapper.convertToDto(reservation));
        }
        return reservationDtoList;
    }

    public List<ReservationDto> getReservationsByCarId(Long carId) {
        List<Reservation> reservationList = reservationRepository.findByCarId(carId);
        List<ReservationDto> reservationDtoList = new ArrayList<>();
        for (Reservation reservation : reservationList) {
            reservationDtoList.add(reservationMapper.convertToDto(reservation));
        }
        return reservationDtoList;
    }

    public List<ReservationDto> getReservationsByBranchId(Long branchId) {
        List<Reservation> reservationList = reservationRepository.findByBranchId(branchId);
        List<ReservationDto> reservationDtoList = new ArrayList<>();
        for (Reservation reservation : reservationList) {
            reservationDtoList.add(reservationMapper.convertToDto(reservation));
        }
        return reservationDtoList;
    }

    protected List<Reservation> findAllByPeriod(PeriodDto periodDto) {



        LocalDate periodDtoFrom = LocalDate.parse(periodDto.getStartDate());
        LocalDate periodDtoTo = LocalDate.parse(periodDto.getEndDate());


        List<Reservation> reservationList = reservationRepository.findAll();
        List<Reservation> availableReservations = reservationList.stream()
                .filter(reservation ->  reservation.getReservationStatus() == ReservationStatus.DECLINED ||
                                        reservation.getReservationStatus() == ReservationStatus.COMPLETED)
                .toList();

        return availableReservations.stream().filter(reservation -> reservation.getDateFrom().isEqual(periodDtoFrom)
                || reservation.getDateFrom().isEqual(periodDtoTo)
                || reservation.getDateTo().isEqual(periodDtoFrom)
                || reservation.getDateTo().isEqual(periodDtoTo)
                || (reservation.getDateFrom().isAfter(periodDtoFrom) && reservation.getDateFrom().isBefore(periodDtoTo))
                || (reservation.getDateTo().isAfter(periodDtoFrom) && reservation.getDateTo().isBefore(periodDtoTo))).toList();
    }
}
