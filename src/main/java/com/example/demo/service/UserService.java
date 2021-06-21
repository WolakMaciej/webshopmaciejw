package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.security.AuthResponse;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface UserService {
    List<User> getAll();

    User getUserById(long id);

    void delete(long id);

    User update(User user);

    UserDetails findByToken(String token);

    UserDetails register(User user);

    AuthResponse login(String username, String password);

    void logout(String username);

    User createNew(User user);

    User findByUsername(String username);
}
