package com.ironbank.proj.repository;

import com.ironbank.proj.models.accounts.CreditCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CreditCardRepository extends JpaRepository <CreditCard, Long> {
}