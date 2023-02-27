package com.synchrony.springboot.service;

import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import com.synchrony.springboot.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.synchrony.springboot.repository.UserRepository;
import ch.qos.logback.core.subst.Token;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    // @Autowired
    // private PasswordEncoder passwordEncoder;

    private final String API_KEY = "YOUR_API_KEY";
    private final String REGISTER_ENDPOINT = "https://api.imgur.com/3/account";
    
    
    public boolean authenticate(String username, String password) {
        List<User> users = getAllUsers().stream().filter(user -> username
        .equals(user.getName())).collect(Collectors.toList());
        User user = users.get(0);
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(API_KEY);
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("username", user.getUsername());
        body.add("password", user.getPassword());
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(body, headers);
        ResponseEntity<Token> responseEntity = restTemplate.exchange(REGISTER_ENDPOINT, HttpMethod.POST, requestEntity, Token.class);
        if(responseEntity.getStatusCode() == HttpStatus.OK) {
            //Registration successful, save account information from the response
            return true;
        } else {
            //Registration failed
            return false;
        }
    }

    public boolean register(User user) {
        try {
            userRepository.save(user);
        } catch(Exception e) {
            return false;
        }
        return true;
    }
    
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<User>();
        userRepository.findAll().forEach(user -> users.add(user));
        return users;
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

    public String encodePassword(String password) {
        // return passwordEncoder.encode(password);
        // TODO: encode password by adding a @Bean to the config PasswordEncoder
        return password;
    }

    public String decodePassword(String password) {
        // return passwordEncoder.decode(password);
        // TODO: decode password by adding a @Bean to the config PasswordEncoder
        return password;
    }
    
    public boolean userAlreadyExists(String username, String phone) {
        // check for if username already exists
        List<User> tempUser = getAllUsers().stream().filter(cur_user->cur_user.getUsername()
        .equals(username)).collect(Collectors.toList());

        // check for if phone # already exists
        List<User> tempUser2 = Collections.<User>emptyList();  
        if (phone!= null && phone!="") {
            tempUser2 = getAllUsers().stream().filter(cur_user->cur_user.getPhone()
            .equals(phone)).collect(Collectors.toList());
        }

        return (tempUser.size() > 0 || tempUser2.size() > 0);
    }
}