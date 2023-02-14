package com.ironbank.proj.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

@Getter
@Setter
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@NoArgsConstructor
public abstract class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    protected Money balance;
    private String secretKey;
    @ManyToOne
    private AccountHolder primaryOwner;
    @ManyToOne
    private Optional<AccountHolder> secondaryOwner;
    private BigDecimal penaltyFee = new BigDecimal(40);
    protected LocalDate creationDate;
    @Enumerated(EnumType.STRING)
    private AccountStatus accountStatus;

    public abstract AccountType getAccountType();

    public Account(BigDecimal penaltyFee, LocalDate creationDate, AccountStatus accountStatus) {
        this.penaltyFee = penaltyFee;
        this.creationDate = creationDate;
        this.accountStatus = accountStatus;
    }

    public Account(Money balance, String secretKey, AccountHolder primaryOwner, Optional<AccountHolder> secondaryOwner, BigDecimal penaltyFee, LocalDate creationDate, AccountStatus accountStatus) {
        this.balance = balance;
        this.secretKey = secretKey;
        this.primaryOwner = primaryOwner;
        this.secondaryOwner = secondaryOwner;
        this.accountStatus = AccountStatus.ACTIVE;
        this.creationDate = creationDate;
        setPenaltyFee(penaltyFee);
    }

    public BigDecimal getPenaltyFee() {
        return penaltyFee;
    }

    public void setPenaltyFee(BigDecimal penaltyFee) {
        if (penaltyFee.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Penalty fee cannot be negative.");
        }
        this.penaltyFee = penaltyFee;
    }

    public Money getBalance() {
        return balance;
    }

    public boolean hasSufficientFunds(Money amount) {
        return balance.getAmount().compareTo(amount.getAmount()) >= 0;
    }
}