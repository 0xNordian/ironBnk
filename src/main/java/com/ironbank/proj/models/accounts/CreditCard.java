package com.ironbank.proj.models.accounts;

import com.ironbank.proj.models.users.AccountHolder;
import com.ironbank.proj.models.users.AccountType;
import com.ironbank.proj.models.Money;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Table(name = "credit_card")
@Entity
public class CreditCard extends Account {

    private BigDecimal creditLimit;
    private BigDecimal interestRate = new BigDecimal("0.2");

    public CreditCard() {
        super();
    }

    public CreditCard(Money balance, String secretKey, AccountHolder primaryOwner, AccountHolder secondaryOwner, BigDecimal creditLimit, BigDecimal interestRate) {
        super(balance, secretKey, primaryOwner, secondaryOwner);
        this.creditLimit = creditLimit;
        this.interestRate = interestRate;
    }

    public BigDecimal getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(BigDecimal creditLimit) {
        if (creditLimit.compareTo(new BigDecimal("100")) < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Credit limit cannot be lower than 100.");
        }
        if (creditLimit.compareTo(new BigDecimal("100000")) > 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Credit limit cannot be higher than 100000.");
        }
        this.creditLimit = creditLimit;
    }

    public BigDecimal getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(BigDecimal interestRate) {
        if (interestRate.compareTo(new BigDecimal("0.1")) < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Interest rate cannot be lower than 0.1.");
        }
        if (interestRate.compareTo(new BigDecimal("0.2")) > 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Interest rate cannot be higher than 0.2.");
        }
        this.interestRate = interestRate;
    }

    @Override
    public AccountType getAccountType() {
        return AccountType.CREDIT_CARD;
    }

    public void applyMonthlyInterest() {
        BigDecimal monthlyInterestRate = interestRate.divide(new BigDecimal("12"), 10, RoundingMode.HALF_EVEN);
        BigDecimal interestAmount = balance.getAmount().multiply(monthlyInterestRate).setScale(2, RoundingMode.HALF_EVEN);
        balance.increaseAmount(interestAmount);
    }

    @Override
    public String toString() {
        return "CreditCard{" +
                "creditLimit=" + creditLimit +
                ", interestRate=" + interestRate +
                ", balance=" + balance +
                ", creationDate=" + creationDate +
                '}';
    }
}