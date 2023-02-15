package com.synchrony.backend.user;

import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;

@RestController
@RequestMapping(path = "api/v1/user")
public class UserController {
    @GetMapping
    public List<User> getUsers() {
        return List.of(
            new User(1L,
            "Disha",
            "Agarwal",
            "123456789",
            "d.agarwal",
            "temp")
        );
    }
}