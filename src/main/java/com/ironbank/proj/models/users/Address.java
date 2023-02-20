package com.ironbank.proj.models.users;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Embeddable
@Getter
@Setter
public class Address {

    private String street;
    private String city;
    private String state;
    private String country;
    private String zipCode;


}
