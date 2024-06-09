package com.sda64.javaremote64finalproject.controller;

import com.sda64.javaremote64finalproject.dto.BranchDto;
import com.sda64.javaremote64finalproject.exception.EntityNotFoundException;
import com.sda64.javaremote64finalproject.service.BranchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/branch")
@CrossOrigin("http://localhost:4200/")
public class BranchController {
    private final BranchService branchService;
    @Autowired
    public BranchController(BranchService branchService) {
        this.branchService = branchService;
    }

    @PostMapping("/create")
    public ResponseEntity<BranchDto> createBranch(@RequestBody BranchDto branchDto) {
        BranchDto createdBranchDto = branchService.createBranch(branchDto);
        return new ResponseEntity<>(createdBranchDto, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BranchDto> getBranch(@PathVariable Long id) throws EntityNotFoundException {
        BranchDto branchFound = branchService.findById(id);
        return new ResponseEntity<>(branchFound, HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<List<BranchDto>> getAllBranches() {
        return new ResponseEntity<>(branchService.findAll(), HttpStatus.OK);
    }
    @GetMapping("/available")
    public ResponseEntity<List<BranchDto>> getAllAvailableBranches() {
        return new ResponseEntity<>(branchService.findAllAvailable(), HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<BranchDto> updateBranch(@RequestBody BranchDto branchDto) throws EntityNotFoundException {
        BranchDto branchUpdated = branchService.updateBranch(branchDto);
        return new ResponseEntity<>(branchUpdated, HttpStatus.OK);
    }
}
