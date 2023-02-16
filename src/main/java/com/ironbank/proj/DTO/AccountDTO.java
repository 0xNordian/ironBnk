package com.ironbank.proj.DTO;

import java.math.BigDecimal;

public class AccountDTO {
    private String balance;
    private Long primaryOwnerId;
    private Long secondaryOwnerId;
    private String minimunBalance;
    private String secretKey;
    private String monthlyMaintenanceFee;
    private String interestRate;
    private String creditLimit;

    public AccountDTO() {
    }

    public AccountDTO(String balance, Long primaryOwnerId, Long secondaryOwnerId, String minimunBalance, String secretKey, String monthlyMaintenanceFee, String interestRate, String penaltyFee, String creditLimit) {
        this.balance = balance;
        this.primaryOwnerId = primaryOwnerId;
        this.secondaryOwnerId = secondaryOwnerId;
        this.minimunBalance = minimunBalance;
        this.secretKey = secretKey;
        this.monthlyMaintenanceFee = monthlyMaintenanceFee;
        this.interestRate = interestRate;
        this.creditLimit = creditLimit;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public Long getPrimaryOwnerId() {
        return primaryOwnerId;
    }

    public void setPrimaryOwnerId(Long primaryOwnerId) {
        this.primaryOwnerId = primaryOwnerId;
    }

    public Long getSecondaryOwnerId() {
        return secondaryOwnerId;
    }

    public void setSecondaryOwnerId(Long secondaryOwnerId) {
        this.secondaryOwnerId = secondaryOwnerId;
    }

    public String getMinimunBalance() {
        return minimunBalance;
    }

    public void setMinimunBalance(String minimunBalance) {
        this.minimunBalance = minimunBalance;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getMonthlyMaintenanceFee() {
        return monthlyMaintenanceFee;
    }

    public void setMonthlyMaintenanceFee(String monthlyMaintenanceFee) {
        this.monthlyMaintenanceFee = monthlyMaintenanceFee;
    }

    public String getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(String interestRate) {
        this.interestRate = interestRate;
    }

    public String getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(String creditLimit) {
        this.creditLimit = creditLimit;
    }
}
