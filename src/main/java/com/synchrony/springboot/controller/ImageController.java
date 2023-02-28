package com.synchrony.springboot.controller;

import java.util.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.synchrony.springboot.model.Image;
import com.synchrony.springboot.model.User;
import com.synchrony.springboot.service.ImageService;
import com.synchrony.springboot.service.SessionService;

@RestController
@RequestMapping("imgur/")
public class ImageController {
    
    @Autowired
    private ImageService imageService;
    @Autowired
    private SessionService sessionService;

    // private final String url = "https://thumbs.dreamstime.com/b/set-elements-fir-tree-branches-christmas-isolated-white-transparent-background-add-png-file--happy-happy-new-year-135296751.jpg";
    
    @PostMapping("/upload")
    public ResponseEntity<Image> uploadImage(@RequestHeader String session, @RequestParam String url) throws Exception {
        // TODO: thread-safe cache for token
        User user = sessionService.getUserFromToken(session).getBody();
        Image image = imageService.uploadImage(user, url);
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
