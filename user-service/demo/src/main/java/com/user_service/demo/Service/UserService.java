package com.user_service.demo.Service;


import com.user_service.demo.Entity.User;
import com.user_service.demo.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Cacheable(cacheNames = "users", key = "#username")
    public User getUserByUsername(String username) {
       return userRepository.findByUsername(username).orElse(null);
    }

}
