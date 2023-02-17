package com.ironbank.proj.services;

import com.ironbank.proj.DTO.AccountDTO;
import com.ironbank.proj.DTO.SavingsDTO;
import com.ironbank.proj.models.accounts.*;
import com.ironbank.proj.models.users.AccountHolder;
import com.ironbank.proj.models.Money;
import com.ironbank.proj.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;

@Service
public class AdminService {
    @Autowired
    AccountHolderRepository accountHolderRepository;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    SavingsRepository savingsRepository;
    @Autowired
    CheckingRepository checkingRepository;
    @Autowired
    StudentCheckingRepository studentCheckingRepository;
    @Autowired
    CreditCardRepository creditCardRepository;

    /*
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

     */

    public Savings createSavingsAcc(SavingsDTO savingsDTO){
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

    public CreditCard createCreditCardAccount(AccountDTO accountDTO){
        System.out.println("createCreditCardAccount from AdminService: " + accountDTO);
        AccountHolder primaryOwner = accountHolderRepository.findById(accountDTO.getPrimaryOwnerId()).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Primary owner not found"));
        AccountHolder secondaryOwner = null;

        if(accountDTO.getSecondaryOwnerId() != null && accountHolderRepository.findById(accountDTO.getSecondaryOwnerId()).isPresent()){
            secondaryOwner = accountHolderRepository.findById(accountDTO.getSecondaryOwnerId()).get();
        }

        CreditCard creditCard = new CreditCard(new Money(new BigDecimal(accountDTO.getBalance())),accountDTO.getSecretKey(), primaryOwner, secondaryOwner,new BigDecimal(accountDTO.getCreditLimit()),new BigDecimal((accountDTO.getInterestRate())));

        return creditCardRepository.save(creditCard);
    }

    public Account createCheckingOrStudChecking(AccountDTO accountDTO){
        System.out.println("createCheckingAccount from AdminService: " + accountDTO);

        AccountHolder primaryOwner = accountHolderRepository.findById(accountDTO.getPrimaryOwnerId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Primary owner not found"));

        AccountHolder secondaryOwner = null;
        if (accountDTO.getSecondaryOwnerId() != null && accountHolderRepository.findById(accountDTO.getSecondaryOwnerId()).isPresent()) {
            secondaryOwner = accountHolderRepository.findById(accountDTO.getSecondaryOwnerId()).get();
        }

        LocalDate dateOfBirth = primaryOwner.getDateOfBirth();
        LocalDate now = LocalDate.now();
        Period age = Period.between(dateOfBirth, now);

        if (age.getYears() >= 24) {
            Checking checkingAccount = new Checking(new Money(new BigDecimal(accountDTO.getBalance())),accountDTO.getSecretKey(), primaryOwner, secondaryOwner);

            return checkingRepository.save(checkingAccount);
        } else if (age.getYears() > 0 ) {
            StudentChecking studentChecking = new StudentChecking(new Money(new BigDecimal(accountDTO.getBalance())),accountDTO.getSecretKey(), primaryOwner, secondaryOwner);

            return studentCheckingRepository.save(studentChecking);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User must be older than 24 years old to open an account.");
        }
    }

}