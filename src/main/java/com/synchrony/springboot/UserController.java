package com.synchrony.springboot;

import com.synchrony.springboot.model.Image;
import com.synchrony.springboot.model.User;
import com.synchrony.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    User getUser(@PathVariable Integer id){
        return  userService.findById(id).get();
    }

    @RequestMapping(value = "/user", method = RequestMethod.POST)
    String addUser(@RequestBody User user){
        User savedUser = userService.save(user);
        return "SUCCESS";
    }

    // @GetMapping("image/{id}")
    // public Image getImage(@PathVariable("id") String id) {
    //     LOGGER.info("getImage called for id {}", id);
    //     return imageService.getImage(id);
    // }
}
