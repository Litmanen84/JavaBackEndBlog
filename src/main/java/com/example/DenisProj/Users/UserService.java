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

    public User registerUser(String username, String email, String password) throws Exception {
        Optional<User> userOptional = repository.findByUsername(username);
        Optional<User> emailOptional = repository.findByEmail(email);
        if (userOptional.isPresent()) {
            throw new Exception("Username already in use");
        }
        if (emailOptional.isPresent()) {
            throw new Exception("Email already in use");
        }
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(crypt.encode(password));
        return repository.save(user);
    }

    public User loginUser(String username, String password) {
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
