package com.ironbank.proj.model;

import jakarta.persistence.Entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

@Entity
public class StudentChecking extends Checking {

    private static final BigDecimal MINIMUM_BALANCE = BigDecimal.ZERO;
    private static final BigDecimal MONTHLY_MAINTENANCE_FEE = BigDecimal.ZERO;

    public StudentChecking() {
        super();
    }

    public StudentChecking(Money balance, String secretKey, AccountHolder primaryOwner,
                           Optional<AccountHolder> secondaryOwner, BigDecimal penaltyFee, LocalDate creationDate) {
        super(balance, secretKey, primaryOwner, secondaryOwner, penaltyFee, creationDate, AccountStatus.ACTIVE);
    }

    @Override
    public AccountType getAccountType() {
        return AccountType.STUDENT_CHECKING;
    }

    @Override
    public BigDecimal getMinimumBalance() {
        return MINIMUM_BALANCE;
    }

    @Override
    public BigDecimal getMonthlyMaintenanceFee() {
        return MONTHLY_MAINTENANCE_FEE;
    }
}
