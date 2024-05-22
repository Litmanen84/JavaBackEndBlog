package com.example.DenisProj.config;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class CustomUserDetails extends User {
    private final boolean isAdmin;

    public CustomUserDetails(String username, String password, Collection<? extends GrantedAuthority> authorities, boolean isAdmin) {
        super(username, password, authorities);
        this.isAdmin = isAdmin;
    }

    public boolean isAdmin() {
        return isAdmin;
    }
}
