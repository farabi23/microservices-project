package com.user_service.demo.Controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.user_service.demo.Dto.AuthRequest;
import com.user_service.demo.Dto.AuthResponse;
import com.user_service.demo.Entity.Role;
import com.user_service.demo.Entity.User;
import com.user_service.demo.Service.UserService;
import com.user_service.demo.Utils.JwtUtil;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(), request.getPassword()));


        String token = jwtUtil.generateToken(request.getUsername());
        return new AuthResponse(token);
    }

    @PostMapping("/register")
    public String register(@RequestBody User user) throws JsonProcessingException {
            if (user.getRole() == null) {
                user.setRole(Role.USER);
            }
            userService.saveUser(user);

            return ("User registered successfully");

    }


}
