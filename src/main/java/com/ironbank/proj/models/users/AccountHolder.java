package com.ironbank.proj.models.users;




import com.ironbank.proj.models.Role;
import com.ironbank.proj.models.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Collection;

@Getter
@Setter
@Entity
public class AccountHolder extends User {

    private LocalDate dateOfBirth;

    @Embedded
    private Address primaryAddress;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "street", column = @Column(name = "mailing_street")),
            @AttributeOverride(name = "city", column = @Column(name = "mailing_city")),
            @AttributeOverride(name = "state", column = @Column(name = "mailing_state")),
            @AttributeOverride(name = "country", column = @Column(name = "mailing_country")),
            @AttributeOverride(name = "zipCode", column = @Column(name = "mailing_zip_code"))
    })
    private Address mailingAddress;

    public AccountHolder(String name, String username, String password, Collection<Role> roles, LocalDate dateOfBirth, Address primaryAddress, Address mailingAddress) {
        super(null, name, username, password, roles);
        this.dateOfBirth = dateOfBirth;
        this.primaryAddress = primaryAddress;
        this.mailingAddress = mailingAddress;
    }

    public AccountHolder() {
        super();
    }
}