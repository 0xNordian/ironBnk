package com.ironbank.proj.models.accounts;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.ironbank.proj.models.users.AccountHolder;
import com.ironbank.proj.models.users.AccountStatus;
import com.ironbank.proj.models.users.AccountType;
import com.ironbank.proj.models.Money;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@NoArgsConstructor
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "accountType")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Checking.class, name = "CHECKING"),
        @JsonSubTypes.Type(value = StudentChecking.class, name = "STUDENT_CHECKING"),
        @JsonSubTypes.Type(value = Savings.class, name = "SAVINGS"),
        @JsonSubTypes.Type(value = CreditCard.class, name = "CREDIT_CARD"),
        @JsonSubTypes.Type(value = Account.class, name = "ACCOUNT")
})
public abstract class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Embedded
    protected Money balance;
    private String secretKey;
    @ManyToOne
    @JoinColumn(name = "primary_owner_id")
    @NotNull
    private AccountHolder primaryOwner;
    @ManyToOne
    @JoinColumn(name = "secondary_owner_id")
    private AccountHolder secondaryOwner;
    @Embedded
    @AttributeOverrides({@AttributeOverride(name = "amount", column = @Column(name = "penalty_fee_amount")),@AttributeOverride(name = "currency", column = @Column(name = "penalty_fee_currency"))})
    private final Money penaltyFee = new Money(new BigDecimal(40));
    protected LocalDate creationDate = LocalDate.now();
    @Enumerated(EnumType.STRING)
    private AccountStatus status;

    public abstract AccountType getAccountType();

    public Account(Money balance, String secretKey, AccountHolder primaryOwner, AccountHolder secondaryOwner) {
        this.balance = balance;
        this.secretKey = secretKey;
        this.primaryOwner = primaryOwner;
        this.secondaryOwner = secondaryOwner;
        this.status = AccountStatus.ACTIVE;
    }
    public Money getPenaltyFee() {
        return penaltyFee;
    }

    public Money getBalance() {
        return balance;
    }

    public boolean hasSufficientFunds(Money amount) {
        return balance.getAmount().compareTo(amount.getAmount()) >= 0;
    }

    public void deductPenaltyFee() {
        if(this instanceof Checking){
        if (balance.getAmount().compareTo(((Checking) this).getMinimumBalance()) < 0) {
            balance.decreaseAmount(penaltyFee.getAmount());
        }
        }
    }
    public void checkMinimumBalance() {
        if (this instanceof Checking) {
            if (((Checking) this).isBelowMinimumBalance()) {
                balance.decreaseAmount(penaltyFee.getAmount());
            }
        } else if (this instanceof Savings) {
            if (((Savings) this).isBelowMinimumBalance()) {
                balance.decreaseAmount(penaltyFee.getAmount());
            }
        }
    }

    public void setBalance(Money balance) {
        this.balance = balance;
        checkMinimumBalance();
    }

    public boolean canWithdraw(BigDecimal amount) {
        return hasSufficientFunds(new Money(amount));
    }

    public void withdraw(BigDecimal amount) {
        if (!canWithdraw(amount)) {
            throw new IllegalArgumentException("Insufficient funds");
        }
        balance.decreaseAmount(new Money(amount));
    }

    public void deposit(BigDecimal amount) {
        balance.increaseAmount(new Money(amount));
    }

}