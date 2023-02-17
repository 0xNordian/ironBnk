package com.ironbank.proj.repository;

import com.ironbank.proj.models.Transaction;
import com.ironbank.proj.models.accounts.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findByOriginAccountOrTargetAccount(Account originAccount, Account targetAccount);

}

