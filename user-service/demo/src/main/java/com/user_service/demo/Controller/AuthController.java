package com.user_service.demo.Controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.user_service.demo.Dto.AuthRequest;
import com.user_service.demo.Dto.AuthResponse;
import com.user_service.demo.Dto.RegisterRequest;
import com.user_service.demo.Entity.Role;
import com.user_service.demo.Entity.User;
import com.user_service.demo.Repository.UserRepository;
import com.user_service.demo.Service.UserService;
import com.user_service.demo.Utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest request) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(), request.getPassword()));

        User user = userService.getUserByEmail(request.getEmail());


        String token = jwtUtil.generateToken(user.getEmail(), user.getId());
        return new AuthResponse(token);
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest registerRequest) throws JsonProcessingException {

        System.out.println("Register request: {} " + registerRequest);

        if(userRepository.existsByEmail(registerRequest.getEmail())) {
            return ResponseEntity.badRequest().body("User with this email already exists!");

        }
        if(userRepository.existsByUsername(registerRequest.getUsername())) {
            return ResponseEntity.badRequest().body("User with this username already exists!");
        }
        User user = new User();
        user.setEmail(registerRequest.getEmail());
        user.setUsername(registerRequest.getUsername());
        user.setPassword(registerRequest.getPassword());
        user.setRole(Role.USER);

        userService.saveUser(user);

            return ResponseEntity.ok("User registered successfully!");

    }


}
