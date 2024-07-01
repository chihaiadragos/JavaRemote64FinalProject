package com.sda64.javaremote64finalproject.entity;

import com.sda64.javaremote64finalproject.enums.AccountType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private AccountType accountType;

    @OneToOne(mappedBy = "user")
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @OneToOne(mappedBy = "user")
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public String getFullName() {
        if (accountType.equals(AccountType.CLIENT)) {
            return customer.getFirstName() + " " + customer.getLastName();
        }
        return employee.getFirstName() + " " + employee.getLastName();
    }
}
