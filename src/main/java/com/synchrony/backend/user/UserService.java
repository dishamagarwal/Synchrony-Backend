package com.synchrony.backend.user;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class UserService {
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
