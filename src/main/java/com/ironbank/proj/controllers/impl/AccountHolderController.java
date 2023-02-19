package com.ironbank.proj.controllers.impl;

import com.ironbank.proj.models.Money;
import com.ironbank.proj.models.accounts.Transfer;
import com.ironbank.proj.services.AdminService;
import com.ironbank.proj.services.TransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/account_holder")
public class AccountHolderController {

    @Autowired
    AdminService adminService;

    @Autowired
    TransferService transferService;

    @GetMapping("/accessAccount/{id}")
    @ResponseStatus(HttpStatus.FOUND)
    public ResponseEntity<Money> getBalanceFromTargetAccount(@PathVariable Long id){
        Money balance = adminService.getBalanceFromAccount(id);
        return ResponseEntity.ok().body(balance);
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
