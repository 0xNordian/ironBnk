package com.ironbank.proj.services;

import com.ironbank.proj.DTO.AccountDTO;
import com.ironbank.proj.DTO.SavingsDTO;
import com.ironbank.proj.models.users.AccountHolder;
import com.ironbank.proj.models.Money;
import com.ironbank.proj.models.accounts.Savings;
import com.ironbank.proj.repository.AccountHolderRepository;
import com.ironbank.proj.repository.AccountRepository;
import com.ironbank.proj.repository.SavingsRepository;
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
    AccountRepository accountRepository;
    @Autowired
    SavingsRepository savingsRepository;

    public Savings createSavingsAcc(AccountDTO accountDTO){
        AccountHolder primaryOwner = accountHolderRepository.findById(accountDTO.getPrimaryOwnerId()).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Primary owner not found"));
        AccountHolder secondaryOwner = null;

        if(accountDTO.getSecondaryOwnerId() != null && accountHolderRepository.findById(accountDTO.getSecondaryOwnerId()).isPresent()){
            secondaryOwner = accountHolderRepository.findById(accountDTO.getSecondaryOwnerId()).get();
        }

        Savings savings = new Savings(new Money(new BigDecimal(accountDTO.getBalance())), accountDTO.getSecretKey(), primaryOwner, secondaryOwner, new BigDecimal((accountDTO.getInterestRate())),new BigDecimal(accountDTO.getMinimunBalance()));

        //return accountRepository.save(savings);
        return savingsRepository.save(savings);
    }

    public Savings createSavingsAcc2(SavingsDTO savingsDTO){
        System.out.println("savingsDTO from AdminService: " + savingsDTO);
        AccountHolder primaryOwner = accountHolderRepository.findById(savingsDTO.getPrimaryOwnerId()).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Primary owner not found"));
        AccountHolder secondaryOwner = null;

        if(savingsDTO.getSecondaryOwnerId() != null && accountHolderRepository.findById(savingsDTO.getSecondaryOwnerId()).isPresent()){
            secondaryOwner = accountHolderRepository.findById(savingsDTO.getSecondaryOwnerId()).get();
        }

        Savings savings = new Savings(new Money(new BigDecimal(savingsDTO.getBalance())), savingsDTO.getSecretKey(), primaryOwner, secondaryOwner, new BigDecimal((savingsDTO.getInterestRate())),new BigDecimal(savingsDTO.getMinimunBalance()));

        //return accountRepository.save(savings);
        return savingsRepository.save(savings);
    }
}