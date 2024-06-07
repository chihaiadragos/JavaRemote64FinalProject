package com.sda64.javaremote64finalproject.service;

import com.sda64.javaremote64finalproject.dto.BranchDto;
import com.sda64.javaremote64finalproject.dto.CarDto;
import com.sda64.javaremote64finalproject.entity.Branch;
import com.sda64.javaremote64finalproject.entity.Car;
import com.sda64.javaremote64finalproject.exception.EntityNotFoundException;
import com.sda64.javaremote64finalproject.mapper.BranchMapper;
import com.sda64.javaremote64finalproject.repository.BranchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BranchService {
    private final BranchRepository branchRepository;
    private final BranchMapper branchMapper;

    @Autowired
    public BranchService(BranchRepository branchRepository, BranchMapper branchMapper) {
        this.branchRepository = branchRepository;
        this.branchMapper = branchMapper;
    }

    public BranchDto createBranch(BranchDto branchDto) {
        Branch branch = branchMapper.convertToEntity(branchDto);
        return branchMapper.convertToDto(branchRepository.save(branch));
    }

    public BranchDto findById(Long id) throws EntityNotFoundException {
        Branch entityBranch = branchRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(String.format("Branch with %s does not exist", id)));
        return branchMapper.convertToDto(entityBranch);
    }

    public BranchDto updateBranch(BranchDto branchDto) throws EntityNotFoundException {
        Branch entityBranch = branchRepository
                .findById(branchDto.getId())
                .orElseThrow(() -> new EntityNotFoundException(String.format("Branch with %s does not exist", branchDto.getId())));
        if (branchDto.getCity() != null && !entityBranch.getCity().equals(branchDto.getCity())) {
            entityBranch.setCity(branchDto.getCity());
        }
        if (branchDto.getAddress() != null && !entityBranch.getAddress().equals(branchDto.getAddress())) {
            entityBranch.setAddress(branchDto.getAddress());
        }
        return branchMapper.convertToDto(branchRepository.save(entityBranch));
    }

    public List<BranchDto> findAll() {
        List<Branch> branchList = branchRepository.findAll();
        List<BranchDto> branchDtoList = new ArrayList<>();
        for (Branch branch : branchList) {
            branchDtoList.add(branchMapper.convertToDto(branch));
        }
        return branchDtoList;
    }

    public BranchDto findByCity(String city) {
        Optional<Branch> entityBranch = branchRepository
                .findByCity(city);
        return branchMapper.convertToDto(entityBranch.get());
    }
}
