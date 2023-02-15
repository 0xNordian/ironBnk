package com.ironbank.proj.DTO;

import java.math.BigDecimal;

public class SavingsDTO {
    private String balance;
    private Long primaryOwnerId;
    private Long secondaryOwnerId;
    private String minimunBalance;
    private String secretKey;
    private String interestRate;

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

    public String getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(String interestRate) {
        this.interestRate = interestRate;
    }

    public BigDecimal getBalanceAsBigDecimal() {
        return new BigDecimal(balance);
    }

    public BigDecimal getInterestRateAsBigDecimal() {
        return new BigDecimal(interestRate);
    }
}
