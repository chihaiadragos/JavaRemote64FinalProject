package com.sda64.javaremote64finalproject.mapper;

import com.sda64.javaremote64finalproject.dto.BranchDto;
import com.sda64.javaremote64finalproject.entity.Branch;
import com.sda64.javaremote64finalproject.enums.EntityStatus;
import com.sda64.javaremote64finalproject.repository.BranchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BranchMapper implements Mapper<Branch, BranchDto> {
    private final BranchRepository branchRepository;

    @Autowired
    public BranchMapper(BranchRepository branchRepository) {
        this.branchRepository = branchRepository;
    }

    @Override
    public BranchDto convertToDto(Branch entity) {

        BranchDto branchDto = new BranchDto();
        branchDto.setId(entity.getId());
        branchDto.setCity(entity.getCity());
        branchDto.setAddress(entity.getAddress());

        return branchDto;
    }

    @Override
    public Branch convertToEntity(BranchDto dto) {

        Branch branch;

        if (dto.getId() != null && dto.getId() >= 0) {
            branch = branchRepository.findById(dto.getId()).orElse(new Branch());

        } else {
            branch = new Branch();
        }

//        branch.setId(dto.getId());
        branch.setCity(dto.getCity());
        branch.setAddress(dto.getAddress());
        if (dto.getStatus() == null) {
            branch.setStatus(EntityStatus.AVAILABLE);
        }
        return branch;
    }
}
