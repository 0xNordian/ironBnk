package com.ironbank.proj.models.users;

import com.ironbank.proj.models.Role;
import com.ironbank.proj.models.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collection;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "admins")
public class Admin extends User {

    public Admin(String name, String username, String password, Collection<Role> roles) {
        super(null, name, username, password, roles);
    }
}