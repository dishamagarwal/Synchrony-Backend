package com.synchrony.springboot.service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.synchrony.springboot.model.Session;
import com.synchrony.springboot.model.User;
import com.synchrony.springboot.repository.SessionRepository;

@Service
public class SessionService {

    @Autowired
    SessionRepository sessionRepository;

    public List<Session> getAllSessions() {
        List<Session> sessions = new ArrayList<Session>();
        sessionRepository.findAll().forEach(session -> sessions.add(session));
        return sessions;
    }

    public Session getSessionById(int id) {
        return sessionRepository.findById(id).get();
    }

    // using CRUD functions to create and delete sessions
    public void saveOrUpdateSession(Session session) {
        sessionRepository.save(session);
    }

    public void deleteSessionById (int id) {
        sessionRepository.deleteById(id);
    }

    public boolean sessionExpired(Session session) {
        return session.getExpirationDate().isBefore(LocalDateTime.now(ZoneOffset.UTC));
    }

    public ResponseEntity<User> getUserFromToken(String token) {
        List<Session> cur = getAllSessions().stream().filter(cur_session->cur_session.getToken()
        .equals(token)).collect(Collectors.toList());
        if (cur.size()<=0) {
            throw new Error("no such session found");
        }
        Session session = cur.get(0);
        if (sessionExpired(session)) {
            throw new Error("token has expired");
        }
        return ResponseEntity.ok(session.getUser());
    }
}
