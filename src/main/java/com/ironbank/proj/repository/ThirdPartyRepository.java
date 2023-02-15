package com.ironbank.proj.repository;

import com.ironbank.proj.model.ThirdParty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ThirdPartyRepository extends JpaRepository<ThirdParty, Long> {

    ThirdParty findByHashedKey(String hashedKey);
}