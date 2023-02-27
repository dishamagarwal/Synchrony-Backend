package com.synchrony.springboot.controller;

import com.synchrony.springboot.model.User;
import com.synchrony.springboot.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("api/v1")
public class UserController {

    @Autowired
    private UserService userService;
    
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String username, @RequestParam String password) throws Exception {
        // TODO: authenticate username password using oauth
        try {
            String token = userService.authenticate(username, password);
            if(token != "") {
                final HttpHeaders httpHeaders = new HttpHeaders(); httpHeaders.setContentType(MediaType.APPLICATION_JSON);
                return new ResponseEntity < String > ("{\"session_token\": \""+token+"\"}", httpHeaders, HttpStatus.OK);
            } else {
                return ResponseEntity.status(HttpStatus.CONFLICT).build();
            }
        } catch (Exception e) {
            final HttpHeaders httpHeaders = new HttpHeaders(); httpHeaders.setContentType(MediaType.APPLICATION_JSON);
            return new ResponseEntity < String > ("{\"Error\": \""+e.getMessage()+"\"}", httpHeaders, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestParam @Valid String username, @RequestParam @Valid String password, 
    @RequestParam(required=false) String phone, @RequestParam(required=false) String firstname, 
    @RequestParam(required=false) String lastname) {
        // encrypt password
        User user = new User(username, userService.encodePassword(password));
        if (!userService.userAlreadyExists(username, phone)) {
            if (phone!=null && phone!="") {
                user.setPhone(phone);
            }
            if (firstname!=null && firstname!="") {
                user.setFirstName(firstname);
            }
            if (lastname!=null && lastname!="") {
                user.setLastName(lastname);
            }
            try {
                if (userService.register(user)) {
                    return ResponseEntity.ok().build();
                    // TODO: redirect to homepage with upload/get/delete image options
                } else {
                    throw new Exception("username or phone# already exists");
                }
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        } else {
            // TIP: can be done to update user details
            // userService.saveOrUpdateUser(user);
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
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