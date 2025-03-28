package com.user_service.demo.Controller;


import com.user_service.demo.Entity.User;
import com.user_service.demo.Repository.UserRepository;
import com.user_service.demo.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController()
@RequestMapping("/test")
public class GetUserController {


    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/get-user/{username}")
    public User getUser(@PathVariable String username) {

        return userService.getUserByUsername(username);
    }

    @GetMapping("/allusers")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
