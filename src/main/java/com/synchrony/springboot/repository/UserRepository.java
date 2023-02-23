package com.synchrony.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.synchrony.springboot.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    // String createUser(User user);
}