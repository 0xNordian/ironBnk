package com.ironbank.proj.controllers;

import com.ironbank.proj.DTO.AccountDTO;
import com.ironbank.proj.DTO.SavingsDTO;
import com.ironbank.proj.models.Money;
import com.ironbank.proj.models.accounts.Account;
import com.ironbank.proj.models.accounts.CreditCard;
import com.ironbank.proj.models.accounts.Savings;
import com.ironbank.proj.services.AdminService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    @Autowired
    AdminService adminService;

    @PostMapping("/savings")
    @ResponseStatus(HttpStatus.CREATED)
    public Savings createSavingsAccount(@RequestBody SavingsDTO savingsDTO) {
        return adminService.createSavingsAcc(savingsDTO);
    }

    @PostMapping("/creditcard")
    @ResponseStatus(HttpStatus.CREATED)
    public CreditCard createCreditCard(@RequestBody AccountDTO accountDTO){
        return adminService.createCreditCardAccount(accountDTO);
    }

    @PostMapping("/checkings")
    @ResponseStatus(HttpStatus.CREATED)
    public Account createCheckingsAccounts(@RequestBody AccountDTO accountDTO) {
        return adminService.createCheckingOrStudChecking(accountDTO);
    }

    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Account updateTargetBalance(@RequestBody Money balance, @PathVariable Long id) {
        return adminService.updateBalance(balance, id);
    }

}


