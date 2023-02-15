package com.ironbank.proj.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.math.BigDecimal;
@Table(name = "student_checking_accounts")
@Entity
public class StudentChecking extends Account {

    private static final BigDecimal MINIMUM_BALANCE = BigDecimal.ZERO;
    private static final BigDecimal MONTHLY_MAINTENANCE_FEE = BigDecimal.ZERO;

    public StudentChecking() {
        super();
    }

    public StudentChecking(Money balance, String secretKey, AccountHolder primaryOwner,
                           AccountHolder secondaryOwner) {
        super(balance, secretKey, primaryOwner, secondaryOwner);
    }

    @Override
    public AccountType getAccountType() {
        return AccountType.STUDENT_CHECKING;
    }


    public BigDecimal getMinimumBalance() {
        return MINIMUM_BALANCE;
    }

    public BigDecimal getMonthlyMaintenanceFee() {
        return MONTHLY_MAINTENANCE_FEE;
    }
}
