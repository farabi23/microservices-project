package com.front_end.front_end.MainPage;


import com.front_end.front_end.config.WebClientConfig;
import com.front_end.front_end.entities.Product;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.net.http.HttpRequest;
import java.util.List;

@Controller
public class MainController {

    private final WebClient webClient;

    public MainController(WebClient webClient) {
        this.webClient = WebClient.builder().baseUrl("http://localhost:8086").build();
    }

    @GetMapping("/")
    public String products(Model model, HttpServletRequest request) {

        Mono<List<Product>> productListMono = webClient.get()
                .uri("/products")
                .retrieve()
                .bodyToFlux(Product.class)
                .collectList();

        List<Product> productList = productListMono.block();

        model.addAttribute("products", productList);
        return "main";
    }
}
