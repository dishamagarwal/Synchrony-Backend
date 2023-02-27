package com.synchrony.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.synchrony.springboot.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    // can be used to find dublicate usernames or phone # before registering a user
    // User findByUsername(String username);
    // User findByPhone(String phone);
    // boolean userAlreadyExists(User user);
}