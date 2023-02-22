package com.synchrony.springboot.service;

import org.springframework.data.jpa.repository.JpaRepository;
import com.synchrony.springboot.model.User;

public interface UserService extends JpaRepository<User, Integer>{
}
