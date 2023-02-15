package com.ironbank.proj.repository;

import com.ironbank.proj.models.accounts.Savings;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SavingsRepository extends JpaRepository <Savings, Long> {
}