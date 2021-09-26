package com.alkemy.challenge.services;

import com.alkemy.challenge.data.entities.User;

import java.util.Optional;

public interface UserService {
    Optional<User> getUserByEmail(String email);
    boolean register(User user);
}
