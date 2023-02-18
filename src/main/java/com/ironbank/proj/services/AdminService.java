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
    public Savings createSavingsAcc(SavingsDTO savingsDTO){
        System.out.println("savingsDTO from AdminService: " + savingsDTO);
        AccountHolder primaryOwner = accountHolderRepository.findById(savingsDTO.getPrimaryOwnerId()).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Primary owner not found"));

        AccountHolder secondaryOwner = null;

        if(savingsDTO.getSecondaryOwnerId() != null && accountHolderRepository.findById(savingsDTO.getSecondaryOwnerId()).isPresent()){
            secondaryOwner = accountHolderRepository.findById(savingsDTO.getSecondaryOwnerId()).get();
        }

        Savings savings = new Savings(new Money(new BigDecimal(savingsDTO.getBalance())), savingsDTO.getSecretKey(), primaryOwner, secondaryOwner, new BigDecimal((savingsDTO.getInterestRate())),new BigDecimal(savingsDTO.getMinimunBalance()));

        return savingsRepository.save(savings);
    }
     */

    public Savings createSavingsAcc(SavingsDTO savingsDTO) {
        AccountHolder primaryOwner = accountHolderRepository.findById(savingsDTO.getPrimaryOwnerId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Primary owner not found"));

        AccountHolder secondaryOwner = null;

        if (savingsDTO.getSecondaryOwnerId() != null) {
            secondaryOwner = accountHolderRepository.findById(savingsDTO.getSecondaryOwnerId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Secondary owner not found"));
        }

        BigDecimal balance = savingsDTO.getBalanceAsBigDecimal();
        BigDecimal minimumBalance = new BigDecimal("1000");
        if (savingsDTO.getMinimunBalance() != null) {
            minimumBalance = new BigDecimal(savingsDTO.getMinimunBalance());
            if (minimumBalance.compareTo(new BigDecimal("100")) < 0) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Minimum balance cannot be less than 100");
            }
        }

        BigDecimal interestRate = new BigDecimal("0.0025");
        if (savingsDTO.getInterestRate() != null && !savingsDTO.getInterestRate().isBlank()) {
            interestRate = new BigDecimal(savingsDTO.getInterestRate());
            if (interestRate.compareTo(new BigDecimal("0.5")) > 0) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Interest rate cannot be greater than 0.5");
            }
        }

        Savings savings = new Savings(new Money(balance), savingsDTO.getSecretKey(), primaryOwner, secondaryOwner, interestRate, minimumBalance);

        return savingsRepository.save(savings);
    }

    /*
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
     */

    public CreditCard createCreditCardAccount(AccountDTO accountDTO) {
        AccountHolder primaryOwner = accountHolderRepository.findById(accountDTO.getPrimaryOwnerId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Primary owner not found"));

        AccountHolder secondaryOwner = null;
        if (accountDTO.getSecondaryOwnerId() != null) {
            secondaryOwner = accountHolderRepository.findById(accountDTO.getSecondaryOwnerId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Secondary owner not found"));
        }

        BigDecimal balance = new BigDecimal(accountDTO.getBalance());

        BigDecimal creditLimit = new BigDecimal("100");
        if (accountDTO.getCreditLimit() != null) {
            creditLimit = new BigDecimal(accountDTO.getCreditLimit());
            if (creditLimit.compareTo(new BigDecimal("100")) < 0) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Credit limit cannot be lower than 100.");
            }
            if (creditLimit.compareTo(new BigDecimal("100000")) > 0) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Credit limit cannot be higher than 100000.");
            }
        }

        BigDecimal interestRate = new BigDecimal("0.2");
        if (accountDTO.getInterestRate() != null) {
            interestRate = new BigDecimal(accountDTO.getInterestRate());
            if (interestRate.compareTo(new BigDecimal("0.1")) < 0) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Interest rate cannot be lower than 0.1.");
            }
            if (interestRate.compareTo(new BigDecimal("0.2")) > 0) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Interest rate cannot be higher than 0.2.");
            }
        }

        CreditCard creditCard = new CreditCard(new Money(balance), accountDTO.getSecretKey(), primaryOwner, secondaryOwner, creditLimit, interestRate);

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

    public Account updateBalance(Money newBalance, Long id){

        if (accountRepository.findById(id).isPresent()){
            Account account = accountRepository.findById(id).get();
            if (newBalance.getAmount() != null) {
                account.setBalance(newBalance);
                return accountRepository.save(account);
            } else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The balance amount must not be null");
            }
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No account found with the given id");
        }
    }

    public void deleteAccount(Long id) {
        if (accountRepository.findById(id).isPresent()) {
            accountRepository.deleteById(id);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer account not found ");
        }
    }

    public Money getBalanceFromAccount(Long id){
        if(accountRepository.findById(id).isPresent()){
            Account account = accountRepository.findById(id).get();
            return account.getBalance();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "There is no account with the given Id");
        }
    }
}