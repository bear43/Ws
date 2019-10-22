package com.example.websocketdemo.service;

import com.example.websocketdemo.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserService {
    User findUserByUsername(String username);
    User createUser(String username, String password);
    User getCurrentUser() throws Exception;
}
