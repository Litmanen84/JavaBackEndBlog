package com.example.DenisProj.Users;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.*;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;


@Entity
@Table(name = "users")
public class User implements UserDetails{

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "user_id")
  private Long id;


  @Column(nullable = false, unique = true)
  private String username;

  @Column(nullable = false)
  private String password;

  @Column(nullable = false, unique = true)
  private String email;

  @Column(nullable = false)
  private boolean is_admin;

  User() {}

  User(String username, String password, String email, boolean is_admin) {

    this.username = username;
    this.password = password;
    this.email = email;
    this.is_admin = is_admin;
  }

  public Long getId() {
    return this.id;
  }

  public String getUsername() {
    return this.username;
  }

  public String getPassword() {
    return this.password;
  }

  public String getEmail() {
    return this.email;
  }

  public boolean getIs_admin() {
    return this.is_admin;
}

  public void setId(Long id) {
    this.id = id;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public void setEmail(String email) {
    if (email == null || !email.contains("@")) {
        throw new IllegalArgumentException("Email is not valid");
    }
    this.email = email;
}

  public void setIs_admin(boolean is_admin) {
    this.is_admin = is_admin;
  }

  @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(is_admin ? "ROLE_ADMIN" : "ROLE_USER"));
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
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof User))
            return false;
        User user = (User) o;
        return Objects.equals(this.id, user.id) && Objects.equals(this.username, user.username)
                && Objects.equals(this.password, user.password) && Objects.equals(this.email, user.email)
                && Objects.equals(this.is_admin, user.is_admin);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.username, this.password, this.email, this.is_admin);
    }

    @Override
    public String toString() {
        return "User{" + "id=" + this.id + ", username='" + this.username + '\'' + ", password='" + this.password + '\''
                + ", email='" + this.email + '\'' + ", is_admin=" + this.is_admin + '}';
    }
}
