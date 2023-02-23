package com.synchrony.springboot.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import com.synchrony.springboot.model.User;

@Service
public interface UserService extends JpaRepository<User, Integer> {
    String createUser(User user);
}
