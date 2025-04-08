package com.front_end.front_end.MainPage;

import com.front_end.front_end.dto.AuthRequest;
import com.front_end.front_end.dto.AuthResponse;
import com.front_end.front_end.utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.reactive.function.client.WebClient;

@Controller
public class AuthController {

    private final WebClient webClient;
    private final JwtUtil jwtUtil;

    public AuthController(WebClient webClient, JwtUtil jwtUtil) {
        this.webClient = WebClient.builder().baseUrl("http://localhost:8086").build();;
        this.jwtUtil = jwtUtil;
    }


    @GetMapping("/auth/login")
    public String login() {
        return "auth";
    }

    @PostMapping("/auth/login")
    public String login(@RequestParam("email") String email,
                        @RequestParam("password") String password, Model model,
                        HttpServletRequest request) {

        AuthResponse authResponse = webClient.post()
                .uri("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new AuthRequest(email, password))
                .retrieve()
                .bodyToMono(AuthResponse.class)
                .block();

        String token = authResponse.getToken();
        String username = jwtUtil.extractUsername(token);

        request.getSession().setAttribute("JWT_TOKEN", token);
        request.getSession().setAttribute("username", username);
        model.addAttribute("username", username);


        return "redirect:/";
    }
    @GetMapping("/auth/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        request.getSession().invalidate();

        return "redirect:/";

    }


}
