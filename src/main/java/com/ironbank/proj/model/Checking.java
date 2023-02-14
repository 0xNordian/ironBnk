package com.ironbank.proj.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
@Table(name = "checking_accounts")
@Entity
public class Checking extends Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "minimum_balance")
    private BigDecimal minimumBalance = new BigDecimal(250);

    @Column(name = "monthly_maintenance_fee")
    private BigDecimal monthlyMaintenanceFee = new BigDecimal(12);

    public Checking() {
    }

    @Override
    public AccountType getAccountType() {
        return null;
    }

    public Checking(Money balance, String secretKey, AccountHolder primaryOwner, AccountHolder secondaryOwner, Money penaltyFee, LocalDate creationDate, AccountStatus accountStatus) {
        super(balance, secretKey, primaryOwner, secondaryOwner, penaltyFee, creationDate, accountStatus);
    }

    public BigDecimal getMinimumBalance() {
        return minimumBalance;
    }

    public void setMinimumBalance(BigDecimal minimumBalance) {
        this.minimumBalance = minimumBalance;
    }

    public BigDecimal getMonthlyMaintenanceFee() {
        return monthlyMaintenanceFee;
    }

    public void setMonthlyMaintenanceFee(BigDecimal monthlyMaintenanceFee) {
        this.monthlyMaintenanceFee = monthlyMaintenanceFee;
    }

    public void deductMonthlyMaintenanceFee() {
        balance.decreaseAmount(monthlyMaintenanceFee);
    }

    public boolean isBelowMinimumBalance() {
        return balance.getAmount().compareTo(minimumBalance) < 0;
    }
}
