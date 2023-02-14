package com.ironbank.proj.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "admins")
public class Admin extends User {

    public Admin(String name, String password) {
        super(name, password);
    }
}