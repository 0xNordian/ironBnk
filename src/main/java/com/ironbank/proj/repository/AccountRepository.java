package com.ironbank.proj.repository;

import com.ironbank.proj.model.Account;
import com.ironbank.proj.model.AccountType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    List<Account> findByPrimaryOwnerUsername(String username);

    List<Account> findBySecondaryOwnerUsername(String username);

    //List<Account> findByAccountType(AccountType accountType);

}