package com.example.DenisProj.Users;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

@CrossOrigin(maxAge = 7200)
@RestController
@RequestMapping("/users")
public class UserController {

  private final UserRepository repository;
  private final UserService service;

  @Autowired
  UserController(UserRepository repository, UserService service) {
    this.repository = repository;
    this.service = service;
  }

  @GetMapping
  public List<User> getAllUsers() {
    return repository.findAll();
  }

  @GetMapping("/{id}")
  User one(@PathVariable Long id) {
      
      return repository.findById(id)
          .orElseThrow(() -> new UserNotFoundException(id));
  }

  @PutMapping("/{id}")
  public User updateUser(@RequestBody User newUser, @PathVariable Long id) {
    
    return repository.findById(id)
      .map(user -> {
        user.setUsername(newUser.getUsername());
        user.setEmail(newUser.getEmail());
        user.setPassword(newUser.getPassword());
        user.setIs_admin(newUser.getIs_admin());
        return repository.save(user);
      })
      .orElseGet(() -> {
        newUser.setId(id);
        return repository.save(newUser);
      });
  }

  @DeleteMapping("/{id}")
  void deleteUser(@PathVariable Long id) {
    repository.deleteById(id);
  }

  @PostMapping("/register")
  public User registerUser(@RequestBody RegisterRequest registerRequest) {
    try {
      return service.registerUser(registerRequest.getUsername(), registerRequest.getEmail(), registerRequest.getPassword());
    } catch (Exception e) {
      throw new ResponseStatusException(
        HttpStatus.BAD_REQUEST, e.getMessage(), e);
    }
  }

  @PostMapping("/login")
    public User loginUser(@RequestBody LoginRequest loginRequest) {
        try {
            return service.loginUser(loginRequest.getUsername(), loginRequest.getPassword());
        } catch (Exception e) {
            throw new ResponseStatusException(
                HttpStatus.UNAUTHORIZED, e.getMessage(), e);
        }
    }
}
