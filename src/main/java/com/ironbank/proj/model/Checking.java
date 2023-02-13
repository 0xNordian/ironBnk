package com.ironbank.proj.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
public class Checking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private BigDecimal balance;
    private String secretKey;
    private String primaryOwner;
    private String secondaryOwner;
    private BigDecimal minimumBalance;
    private BigDecimal penaltyFee;
    private BigDecimal monthlyMaintenanceFee;
    private LocalDate creationDate;
    private Enum status;

    public Checking() {
    }

    public Checking(BigDecimal balance, String secretKey, String primaryOwner, String secondaryOwner, BigDecimal minimumBalance, BigDecimal penaltyFee, BigDecimal monthlyMaintenanceFee, LocalDate creationDate, Enum status) {
        setBalance(balance);
        setSecretKey(secretKey);
        setPrimaryOwner(primaryOwner);
        setSecondaryOwner(secondaryOwner);
        setMinimumBalance(minimumBalance);
        setPenaltyFee(penaltyFee);
        setMonthlyMaintenanceFee(monthlyMaintenanceFee);
        setCreationDate(creationDate);
        setStatus(status);
    }

    public Checking(BigDecimal balance, String secretKey, String primaryOwner, String secondaryOwner, BigDecimal penaltyFee, LocalDate creationDate, Enum status) {
        setBalance(balance);
        setSecretKey(secretKey);
        setPrimaryOwner(primaryOwner);
        setSecondaryOwner(secondaryOwner);
        setPenaltyFee(penaltyFee);
        setCreationDate(creationDate);
        setStatus(status);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getPrimaryOwner() {
        return primaryOwner;
    }

    public void setPrimaryOwner(String primaryOwner) {
        this.primaryOwner = primaryOwner;
    }

    public String getSecondaryOwner() {
        return secondaryOwner;
    }

    public void setSecondaryOwner(String secondaryOwner) {
        this.secondaryOwner = secondaryOwner;
    }

    public BigDecimal getMinimumBalance() {
        return minimumBalance;
    }

    public void setMinimumBalance(BigDecimal minimumBalance) {
        this.minimumBalance = minimumBalance;
    }

    public BigDecimal getPenaltyFee() {
        return penaltyFee;
    }

    public void setPenaltyFee(BigDecimal penaltyFee) {
        this.penaltyFee = penaltyFee;
    }

    public BigDecimal getMonthlyMaintenanceFee() {
        return monthlyMaintenanceFee;
    }

    public void setMonthlyMaintenanceFee(BigDecimal monthlyMaintenanceFee) {
        this.monthlyMaintenanceFee = monthlyMaintenanceFee;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public Enum getStatus() {
        return status;
    }

    public void setStatus(Enum status) {
        this.status = status;
    }
}