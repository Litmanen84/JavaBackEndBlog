package com.example.DenisProj.Users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository repository;

    @Autowired
    public PasswordEncoder crypt;

    public User registerUser(User user) throws Exception {
        if (repository.findByEmail(user.getEmail()).isPresent()) {
            throw new Exception("Email already in use");
        }
        if (repository.findByUsername(user.getUsername()).isPresent()) {
            throw new Exception("Username already in use");
        }
        if (Boolean.valueOf(user.getIsAdmin()) == null) {
            user.setIsAdmin(false);
        }
        user.setPassword(crypt.encode(user.getPassword()));
        return repository.save(user);
    }

    public User loginUser(String username, String email, String password) {
        Optional<User> userOptional = repository.findByUsername(username);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (crypt.matches(password, user.getPassword())) {
                return user;
            } else {
                throw new IllegalArgumentException("Invalid password");
            }
        } else {
            throw new IllegalArgumentException("User not found");
        }
    }

    public Optional<User> findByEmail(String email) {
        return repository.findByEmail(email);
    }

    public Optional<User> findByUsername(String username) {
        return repository.findByUsername(username);
    }

    public User saveUser(User user) {
        return repository.save(user);
    }
}
