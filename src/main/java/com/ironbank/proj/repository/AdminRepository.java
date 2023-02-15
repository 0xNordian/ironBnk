package com.ironbank.proj.repository;

import com.ironbank.proj.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {

    //Admin findByUsername(String username);
}