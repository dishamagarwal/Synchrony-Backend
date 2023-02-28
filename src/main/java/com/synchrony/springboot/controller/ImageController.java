package com.synchrony.springboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<Image> getImage(@RequestHeader String session, @PathVariable("id") String id) throws Exception {
        User user = sessionService.getUserFromToken(session).getBody();
        Image image = imageService.getImage(id, user);
        return ResponseEntity.ok(image);
    }
    
    @DeleteMapping("delete/{id}")
    public ResponseEntity<String> deleteImage(@RequestHeader String session, @PathVariable("id") String id) {
        User user = sessionService.getUserFromToken(session).getBody();
        return imageService.deleteImage(user, id);
    }
}
