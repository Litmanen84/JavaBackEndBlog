package com.example.DenisProj.config;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class CustomUserDetails extends User {
    private final boolean is_admin;

    public CustomUserDetails(String username, String password, Collection<? extends GrantedAuthority> authorities, boolean is_admin) {
        super(username, password, authorities);
        this.is_admin = is_admin;
    }

    public boolean is_admin() {
        return is_admin;
    }
}
