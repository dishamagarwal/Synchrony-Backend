package com.synchrony.springboot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.synchrony.springboot.model.Session;
import com.synchrony.springboot.model.User;
import com.synchrony.springboot.repository.SessionRepository;

@Service
public class SessionService {
    @Autowired
    SessionRepository sessionRepository;

    // public boolean saveSession(Session session) {
    //     try {

    //     }
    // }

    // public Session getSessionByUser(User user) {
    //     // return sessionRepository.findAll(Example<User> user2);
    // }
}
