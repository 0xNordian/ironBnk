package com.ironbank.proj.controllers;

import com.ironbank.proj.DTO.SavingsDTO;
import com.ironbank.proj.models.accounts.Savings;
import com.ironbank.proj.services.AdminService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    @Autowired
    AdminService adminService;

    @PostMapping("/savings")
    public Savings createSavingsAccount(@RequestBody SavingsDTO savingsDTO) {
        return adminService.createSavingsAcc(savingsDTO);
    }
}

