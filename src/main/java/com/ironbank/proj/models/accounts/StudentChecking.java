package com.ironbank.proj.models.accounts;

import com.ironbank.proj.models.users.AccountHolder;
import com.ironbank.proj.models.users.AccountType;
import com.ironbank.proj.models.Money;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "student_checking_accounts")
@Getter
@Setter
@Entity
@NoArgsConstructor
public class StudentChecking extends Account {

    public StudentChecking(Money balance, String secretKey, AccountHolder primaryOwner, AccountHolder secondaryOwner) {
        super(balance, secretKey, primaryOwner, secondaryOwner);
    }

    @Override
    public AccountType getAccountType() {
        return AccountType.STUDENT_CHECKING;
    }

/*
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

 */
}
