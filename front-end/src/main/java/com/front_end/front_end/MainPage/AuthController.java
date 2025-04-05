package com.front_end.front_end.MainPage;

import com.front_end.front_end.dto.AuthRequest;
import com.front_end.front_end.dto.AuthResponse;
import jakarta.servlet.http.HttpServletRequest;
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

    public AuthController(WebClient webClient) {
        this.webClient = WebClient.builder().baseUrl("http://localhost:8086").build();;
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

        request.getSession().setAttribute("JWT_TOKEN", token);

        return "redirect:/";
    }


}
