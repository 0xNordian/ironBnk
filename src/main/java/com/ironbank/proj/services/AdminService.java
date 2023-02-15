package com.ironbank.proj.services;

import com.ironbank.proj.DTO.AccountDTO;
import com.ironbank.proj.model.AccountHolder;
import com.ironbank.proj.model.Money;
import com.ironbank.proj.model.Savings;
import com.ironbank.proj.repository.AccountHolderRepository;
import com.ironbank.proj.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;

@Service
public class AdminService {
    @Autowired
    AccountHolderRepository accountHolderRepository;
    @Autowired
    private AccountRepository accountRepository;

    public Savings updateSavingsAcc(AccountDTO accountDTO){
        AccountHolder primaryOwner = accountHolderRepository.findById(accountDTO.getPrimaryOwnerId()).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Primary owner not found"));

        AccountHolder secondaryOwner = null;

        if(accountDTO.getSecondaryOwnerId() != null && accountHolderRepository.findById(accountDTO.getSecondaryOwnerId()).isPresent()){
            secondaryOwner = accountHolderRepository.findById(accountDTO.getSecondaryOwnerId()).get();
        }

        Savings savings = new Savings(new Money(new BigDecimal(accountDTO.getBalance())), accountDTO.getSecretKey(), primaryOwner, secondaryOwner, new BigDecimal((accountDTO.getInterestRate())),new BigDecimal(accountDTO.getMinimunBalance()));

        return accountRepository.save(savings);
    }
}