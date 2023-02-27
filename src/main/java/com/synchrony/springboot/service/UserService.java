package com.synchrony.springboot.service;

import org.springframework.stereotype.Service;
import com.synchrony.springboot.model.Session;
import com.synchrony.springboot.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import com.synchrony.springboot.repository.SessionRepository;
import com.synchrony.springboot.repository.UserRepository;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    SessionRepository sessionRepository;

    // @Autowired
    // private PasswordEncoder passwordEncoder;
    
    
    public boolean authenticate(String username, String password) throws Exception {
        List<User> temp = getAllUsers();
        List<User> users = temp.stream().filter(user -> username
        .equals(user.getUsername())).collect(Collectors.toList());
        try {
            // TODO: check if there is a non-expired session for the user
            // if there is then auto log in the user else:
            if (users.size() <= 0) {
                throw new Exception("incorrect username");
            }
            User user = users.get(0);
            if (decodePassword(user.getPassword()).equals(decodePassword(password))) {
            } else {
                throw new Exception("incorrect password");
            }
            
            // creating new session
            Session session = new Session(user);
            sessionRepository.save(session);
            // checking if the session is expired
            if (session.getExpirationDate().isBefore(LocalDateTime.now(ZoneOffset.UTC))) {
                throw new Exception("session key expired");
                // TODO: logout the user
            }
            // TODO: auto login the user if not expired
        } catch (Exception e) {
            return false;
        }
        return true;
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