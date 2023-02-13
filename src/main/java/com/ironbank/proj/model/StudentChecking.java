package com.ironbank.proj.model;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Getter
@Setter
public class StudentChecking extends Checking {

    public StudentChecking() {
    }

    public StudentChecking(BigDecimal balance, String secretKey, String primaryOwner, String secondaryOwner, BigDecimal penaltyFee, LocalDate creationDate, Enum status) {
        super(balance, secretKey, primaryOwner, secondaryOwner, penaltyFee, creationDate, status);
    }
}
