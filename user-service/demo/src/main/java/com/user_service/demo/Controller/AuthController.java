package com.user_service.demo.Controller;


import com.user_service.demo.Dto.AuthRequest;
import com.user_service.demo.Dto.AuthResponse;
import com.user_service.demo.Entity.Role;
import com.user_service.demo.Entity.User;
import com.user_service.demo.Service.UserService;
import com.user_service.demo.Utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
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
    public String register(@RequestBody User user) {
        if (user.getRole() == null) {  // Ensure the role is not null
            user.setRole(Role.USER);  // Set a default role
        }
        userService.saveUser(user);
        return "User registered successfully!";
    }


}
