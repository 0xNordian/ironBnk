package com.ironbank.proj.repository;


import com.ironbank.proj.models.users.AccountHolder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountHolderRepository extends JpaRepository<AccountHolder, Long> {

    AccountHolder findByUsername(String username);
}