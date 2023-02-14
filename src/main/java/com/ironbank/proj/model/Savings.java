package com.ironbank.proj.model;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Savings extends Account {
    private BigDecimal interestRate = new BigDecimal("0.0025");
    private BigDecimal minimumBalance = new BigDecimal("1000");

    public Savings(Money balance, String secretKey, AccountHolder primaryOwner, AccountHolder secondaryOwner, Money penaltyFee, LocalDate creationDate, AccountStatus accountStatus, BigDecimal interestRate, BigDecimal minimumBalance) {
        super(balance, secretKey, primaryOwner, secondaryOwner, penaltyFee, creationDate, accountStatus);
        this.interestRate = interestRate;
        this.minimumBalance = minimumBalance;
    }

    @Override
    public AccountType getAccountType() {
        return AccountType.SAVINGS;
    }

    public void setInterestRate(BigDecimal interestRate) {
        if (interestRate.compareTo(new BigDecimal("0.005")) > 0) {
            throw new IllegalArgumentException("Interest rate cannot be greater than 0.5.");
        }
        this.interestRate = interestRate;
    }

    public void setMinimumBalance(BigDecimal minimumBalance) {
        if (minimumBalance.compareTo(new BigDecimal("100")) < 0) {
            throw new IllegalArgumentException("Minimum balance cannot be less than 100.");
        }
        this.minimumBalance = minimumBalance;
    }

    public BigDecimal calculateInterest() {
        BigDecimal years = BigDecimal.valueOf(LocalDate.now().getYear() - creationDate.getYear());
        BigDecimal interest = balance.getAmount().multiply(interestRate).multiply(years);
        balance.increaseAmount(interest);
        return interest;
    }

    public boolean isBelowMinimumBalance() {
        return balance.getAmount().compareTo(minimumBalance) < 0;
    }
}
