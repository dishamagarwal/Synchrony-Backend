package com.synchrony.springboot.service;

import org.springframework.stereotype.Service;
import com.synchrony.springboot.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import com.synchrony.springboot.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<User>();
        userRepository.findAll().forEach(user -> users.add(user));
        return  users;
    }

    public User getUserById(int id) {
        return userRepository.findById(id).get();
    }

    // using CRUD functions to create and delete user
    public void saveOrUpdateUser(User user) {
        userRepository.save(user);
    }

    public void deleteUserById (int id) {
        userRepository.deleteById(id);
    }
}