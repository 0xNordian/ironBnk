package com.ironbank.proj.controllers.impl;

import com.ironbank.proj.DTO.AccountDTO;
import com.ironbank.proj.DTO.SavingsDTO;
import com.ironbank.proj.models.Money;
import com.ironbank.proj.models.accounts.Account;
import com.ironbank.proj.models.accounts.CreditCard;
import com.ironbank.proj.models.accounts.Savings;
import com.ironbank.proj.models.accounts.Transfer;
import com.ironbank.proj.services.AdminService;
import com.ironbank.proj.services.TransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    @Autowired
    AdminService adminService;

    @Autowired
    TransferService transferService;

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

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deleteTargetAccount(@PathVariable Long id) {
        adminService.deleteAccount(id);
    }

    @PostMapping("/transferFromChecking")
    @ResponseStatus(HttpStatus.OK)
    public void transferFromChecking(@RequestBody Transfer transfer) {
        transferService.transferMoneyFromChecking(transfer.getSourceAccountId(), transfer.getTargetAccountId(), transfer.getAmount(), transfer.getTransferRequest().getOwnerName());
    }

    @PostMapping("/transferFromSaving")
    @ResponseStatus(HttpStatus.OK)
    public void transferFromSaving(@RequestBody Transfer transfer) {
        transferService.transferMoneyFromSaving(transfer.getSourceAccountId(), transfer.getTargetAccountId(), transfer.getAmount(), transfer.getTransferRequest().getOwnerName());
    }

}


