package com.uroom.backend.auth.services;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.uroom.backend.Models.EntitiyModels.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

public class UserDetailsImpl implements UserDetails {
    private static final long serialVersionUID = 1L;

    private String email;

    private String name;

    @JsonIgnore
    private String password;
    private Collection<? extends GrantedAuthority> authorities;

    public UserDetailsImpl(String email, String name, String password,
                            Collection<? extends GrantedAuthority> authorities) {

        this.email = email;
        this.name=name;
        this.password = password;
        this.authorities = authorities;
    }

    public static UserDetailsImpl build(User user) {
        List<GrantedAuthority> authorities;
        if(user.isIs_student()){
            authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_STUDENT"));
        }else{
            authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_OWNER"));
        }


        return new UserDetailsImpl(
                user.getEmail(),
                user.getName(),
                user.getPassword(),
                authorities);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return null;
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

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
