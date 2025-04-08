package com.front_end.front_end.MainPage;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.reactive.function.client.WebClient;

@Controller
public class AddProductController {

    private final WebClient webClient;

    public AddProductController(WebClient webClient) {
        this.webClient = WebClient.builder().baseUrl("http://localhost:8086").build();
    }


    @PostMapping("/add")
    public void addProduct() {

    }
}
