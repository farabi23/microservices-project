package com.example.cart_service.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.WebFilter;

@Configuration
public class CorsConfig {

    @Bean
    public WebFilter corsFilter() {
        return (exchange, chain) -> {
            ServerHttpResponse response = exchange.getResponse();
            response.getHeaders().add("Access-Control-Allow-Origin", "*");
            response.getHeaders().add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
            response.getHeaders().add("Access-Control-Allow-Headers", "*");
            return chain.filter(exchange);
        };
    }

}
