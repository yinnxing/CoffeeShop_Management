package com.example.project_server.service;

import com.example.project_server.entity.User;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface UserService {
    List<User> getAllUsers();
}
