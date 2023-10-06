package com.cydeo.entity.common;

import com.cydeo.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Gets the User's details from the database.
 * The main purpose of the UserDetails is representing a user's identity and
 * providing essential information about that user to the security system.
 * Spring boot security compares the User entry and what is coming from
 * the database to approve successful login.
 * Here the Custom class is taking the User class as a parameter.
 * User class which interacts with the User entry details in the UI.
 */
public class UserPrincipal implements UserDetails {

    private final User user;

    public UserPrincipal(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorityList = new ArrayList<>();
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(this.user.getRole().getDescription());
        authorityList.add(grantedAuthority);
        return authorityList;
    }

    @Override
    public String getPassword() {
        return this.user.getPassWord();
    }

    @Override
    public String getUsername() {
        return this.user.getUserName();
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
        return this.user.isEnabled();//validates the enabled user from the db
    }

    public Long getId() {
        return this.user.getId();
    }
}
