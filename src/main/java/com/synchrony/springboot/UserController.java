package com.synchrony.springboot;

import com.synchrony.springboot.model.Image;
import com.synchrony.springboot.model.User;
import com.synchrony.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    User getUser(@PathVariable Integer id){
        return  userService.findById(id).get();
    }

    @RequestMapping(value = "/user", method = RequestMethod.POST)
    String addUser(@RequestBody String username, @RequestBody String password, 
    @RequestBody(required=false) String firstname, @RequestBody(required=false) String lastname, 
    @RequestBody(required=false) String phone){
        User user = new User(username, password);
        if (firstname != null || firstname != "") {
            user.setFirstName(firstname);
        }
        if (lastname != null || lastname != "") {
            user.setLastName(lastname);
        }
        if (phone != null || phone != "") {
            user.setPhone(phone);
        }
        // TODO: adding user to database
        try {
            userService.createUser(user);
        } catch(Exception e) {
            System.out.println(e);
        }
        return "SUCCESS";
    }

    

    // @GetMapping("image/{id}")
    // public Image getImage(@PathVariable("id") String id) {
    //     LOGGER.info("getImage called for id {}", id);
    //     return imageService.getImage(id);
    // }
}
