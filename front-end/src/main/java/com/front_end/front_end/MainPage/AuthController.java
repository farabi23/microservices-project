package com.front_end.front_end.MainPage;

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
        this.webClient = WebClient.builder().baseUrl("http://localhost:8081").build();;
    }


    @GetMapping("/auth/login")
    public String login() {
        return "auth";
    }

    @PostMapping("/auth/login")
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password, Model model) {

        return "redirect:/";
    }


}
