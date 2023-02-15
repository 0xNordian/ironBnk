package com.ironbank.proj.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Address {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    private String street;
    private String city;
    private String state;
    private String country;
    private String zipCode;


}