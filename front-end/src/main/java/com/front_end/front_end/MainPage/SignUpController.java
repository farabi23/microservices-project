package com.front_end.front_end.MainPage;

import com.front_end.front_end.dto.RegisterRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientException;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class SignUpController {


    private final WebClient webClient;

    public SignUpController(WebClient webClient) {
        this.webClient = WebClient.builder().baseUrl("http://localhost:8086").build();;
    }

    @GetMapping("/auth/register")
    public String register() {
        return "signup";
    }

    @PostMapping("/auth/register")
    public String register(@RequestParam("email") String email,
                           @RequestParam("username") String username,
                           @RequestParam("password") String password,
                           @RequestParam("confirm-password") String confirmPassword,
                           RedirectAttributes redirectAttributes) {

        if(!password.equals(confirmPassword)) {
            redirectAttributes.addFlashAttribute("error", "Passwords do not match!");
            return "redirect:/auth/register";
        }


        RegisterRequest request = new RegisterRequest();
        request.setEmail(email);
        request.setUsername(username);
        request.setPassword(password);

        try {
            String response = webClient.post()
                    .uri("/auth/register")
                    .bodyValue(request)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
            redirectAttributes.addFlashAttribute("success", response);
            return "redirect:/auth/success";
        }
        catch (WebClientResponseException e) {
            redirectAttributes.addFlashAttribute("error", e.getResponseBodyAsString());
            return "redirect:/auth/register";
        }

    }

    @GetMapping("/auth/success")
    public String success() {
        return "success";
    }


}
