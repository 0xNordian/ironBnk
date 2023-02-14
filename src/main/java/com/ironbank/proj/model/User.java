package com.ironbank.proj.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Optional;

@Entity
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String userName;
    private LocalDate dateOfBirth;
    @OneToOne(cascade = CascadeType.ALL)
    private Address primaryAddress;
    private String mailingAddress;
    private String hashedKey;
    private String role;

    public User() {
    }

    public User(String userName, LocalDate dateOfBirth, Address primaryAddress, String mailingAddress, String hashedKey, String role) {
        this.userName = userName;
        this.dateOfBirth = dateOfBirth;
        this.primaryAddress = primaryAddress;
        this.mailingAddress = mailingAddress;
        this.hashedKey = hashedKey;
        this.role = role;
    }
}
