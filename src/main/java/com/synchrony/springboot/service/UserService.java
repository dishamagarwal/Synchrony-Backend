package com.synchrony.springboot.service;

import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import com.synchrony.springboot.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import com.synchrony.springboot.repository.UserRepository;
import ch.qos.logback.core.subst.Token;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    private final String API_KEY = "YOUR_API_KEY";
    private final String LOGIN_ENDPOINT = "https://api.imgur.com/oauth2/token";
    private final String REGISTER_ENDPOINT = "https://api.imgur.com/3/account";
    private final String REFRESH_TOKEN = "b02b1a8164fd73cbe0c1189f236bd03794922b6e";
    private final String CLIENT_ID = "0db31a4457dcdc0";
    private final String CLIENT_SECRET = "40c3d3c9e7051cc379f4fbf77c7aedec096c0f60";
    private final String GRANT_TYPE = "";
    
    
    public boolean authenticate(User user) {
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
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        headers.add("username", user.getUsername());
        headers.add("password", user.getPassword());
        headers.add("Authorization", "b02b1a8164fd73cbe0c1189f236bd03794922b6e");
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(body, headers);
        ResponseEntity<Token> responseEntity = restTemplate.exchange(LOGIN_ENDPOINT, HttpMethod.POST, requestEntity, Token.class);
        if(responseEntity.getStatusCode() == HttpStatus.OK) {
            //Registration successful, save account information from the response
            return true;
        } else {
            //Registration failed
            return false;
        }
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
}