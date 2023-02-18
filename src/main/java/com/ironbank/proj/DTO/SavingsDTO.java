package com.ironbank.proj.DTO;

import java.math.BigDecimal;

public class SavingsDTO {
    private String balance;
    private Long primaryOwnerId;
    private Long secondaryOwnerId;
    private String minimunBalance;
    private String secretKey;
    private String monthlyMaintenanceFee;
    private String interestRate;

    public SavingsDTO() {
    }

    public SavingsDTO(String balance, Long primaryOwnerId, Long secondaryOwnerId, String minimunBalance, String secretKey, String monthlyMaintenanceFee) {
        this.balance = balance;
        this.primaryOwnerId = primaryOwnerId;
        this.secondaryOwnerId = secondaryOwnerId;
        this.minimunBalance = minimunBalance;
        this.secretKey = secretKey;
        this.monthlyMaintenanceFee = monthlyMaintenanceFee;
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
        if (interestRate == null || interestRate.isBlank() || interestRate.equals("0")) {
            return null;
        }
        return new BigDecimal(interestRate);
    }

    @Override
    public String toString() {
        return "SavingsDTO{" +
                "balance='" + balance + '\'' +
                ", primaryOwnerId=" + primaryOwnerId +
                ", secondaryOwnerId=" + secondaryOwnerId +
                ", minimunBalance='" + minimunBalance + '\'' +
                ", secretKey='" + secretKey + '\'' +
                ", monthlyMaintenanceFee='" + monthlyMaintenanceFee + '\'' +
                ", interestRate='" + interestRate + '\'' +
                '}';
    }
}
