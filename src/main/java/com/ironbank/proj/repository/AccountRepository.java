package com.ironbank.proj.repository;

import com.ironbank.proj.models.accounts.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    List<Account> findByPrimaryOwnerUsername(String username);

    List<Account> findBySecondaryOwnerUsername(String username);

    @Query("SELECT a FROM Account a WHERE a.id = :id")
    Account getAccountById(@Param("id") Long id);

}