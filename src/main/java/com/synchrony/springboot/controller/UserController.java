package com.synchrony.springboot.controller;

import com.synchrony.springboot.model.User;
import com.synchrony.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
// import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1")
public class UserController {

    @Autowired
    private UserService userService;
    
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user) {
        if(userService.authenticate(user)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @GetMapping("/register")
    public ResponseEntity<String> register(@RequestBody User user) {
        List<User> tempUser = getAllUsers().stream().filter(cur_user->cur_user.getUsername()
        .equals(user.getUsername())).collect(Collectors.toList());
        if (tempUser.size() <= 0) {
            try {
                userService.register(user);
                userService.saveOrUpdateUser(user);
                return ResponseEntity.ok().build();
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @GetMapping(value="/users")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping(value="/users/{id}")
    public User getUserById(@PathVariable("id") int id) {
        return userService.getUserById(id);
    }

    @PostMapping(value="/users")
    public void addOrUpdateUser(@RequestBody User user) {
        userService.saveOrUpdateUser(user);
    }
    
    @DeleteMapping(value="/users/{id}")
    public void deleteUserById(@PathVariable("id") int id) {
        userService.deleteUserById(id);
    }
}
