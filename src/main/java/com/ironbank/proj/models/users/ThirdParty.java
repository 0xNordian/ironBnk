package com.ironbank.proj.models.users;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "third_party")
public class ThirdParty extends User {

    private String hashedKey;
    private String name;
}