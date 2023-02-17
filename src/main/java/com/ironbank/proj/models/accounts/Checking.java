package com.ironbank.proj.models.accounts;

import com.ironbank.proj.models.users.AccountHolder;
import com.ironbank.proj.models.users.AccountType;
import com.ironbank.proj.models.Money;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
@Table(name = "checking_accounts")
@Getter
@Setter
@Entity
public class Checking extends Account {

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

    public Checking(Money balance, String secretKey, AccountHolder primaryOwner, AccountHolder secondaryOwner, BigDecimal minimumBalance, BigDecimal monthlyMaintenanceFee) {
        super(balance, secretKey, primaryOwner, secondaryOwner);
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