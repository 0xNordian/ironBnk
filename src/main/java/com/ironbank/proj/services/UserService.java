package com.ironbank.proj.services;

import com.ironbank.proj.model.User;
import com.ironbank.proj.model.UserRole;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class UserService {

    public User createUser(String name, LocalDate dateOfBirth, String primaryAddress, String mailingAddress, UserRole role, String hashedKey) {
        if (role == UserRole.ACCOUNT_HOLDER) {
            return new User(name, dateOfBirth, primaryAddress, mailingAddress, null, role.name());
        } else if (role == UserRole.THIRD_PARTY) {
            return new User(name, dateOfBirth, primaryAddress, null, hashedKey, role.name());
        } else {
            return new User(name, null, primaryAddress, null, null, role.name());
        }
    }

}
