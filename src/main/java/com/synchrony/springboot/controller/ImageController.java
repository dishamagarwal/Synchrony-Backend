package com.synchrony.springboot.controller;

import java.util.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.synchrony.springboot.model.Image;
import com.synchrony.springboot.model.User;
import com.synchrony.springboot.service.ImageService;

@RestController
@RequestMapping("imgur/")
public class ImageController {
    
    @Autowired
    private ImageService imageService;
    
    @PostMapping("/upload")
    public ResponseEntity<Image> uploadImage(@RequestHeader String session) throws Exception {
        // User user = extractUserFromHeader(session);
        User user = new User();
        Image image = imageService.uploadImage(user);
        return ResponseEntity.ok(image);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Image> getImage(@PathVariable("id") String id) {
        Image image = imageService.getImage(id);
        return ResponseEntity.ok(image);
    }
    
    @DeleteMapping("delete/{id}")
    public ResponseEntity<Void> deleteImage(@RequestHeader("Authorization") String authHeader, @PathVariable("id") String id) {
        User user = extractUserFromHeader(authHeader);
        if(imageService.deleteImage(user, id)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
    
    private User extractUserFromHeader(String authorizationHeader) {
        String base64Credentials = authorizationHeader.substring("Basic".length()).trim();
        byte[] decodedBytes = Base64.getDecoder().decode(base64Credentials);
        String decodedCredentials = new String(decodedBytes);
        String[] credentialsArray = decodedCredentials.split(":", 2);
        String username = credentialsArray[0];
        String password = credentialsArray[1];
        return new User(username, password);
    }
}
