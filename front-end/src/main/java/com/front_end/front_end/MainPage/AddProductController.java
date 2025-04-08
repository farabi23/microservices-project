package com.front_end.front_end.MainPage;


import com.front_end.front_end.dto.ItemDto;
import com.front_end.front_end.entities.Cart;
import com.front_end.front_end.entities.CartItem;
import com.front_end.front_end.utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.util.Map;

@Controller
public class AddProductController {

    private final WebClient webClient;
    private final JwtUtil jwtUtil;

    public AddProductController(WebClient webClient, JwtUtil jwtUtil) {
        this.webClient = WebClient.builder().baseUrl("http://localhost:8086").build();
        this.jwtUtil = jwtUtil;
    }


    @PostMapping("/cart/add")
    public ResponseEntity<Cart> addProduct(@RequestParam("productName") String name,
                                                          @RequestParam("unitPrice") BigDecimal price,
                                                          HttpServletRequest request
                                                          ) {
        String token = (String) request.getSession().getAttribute("JWT_TOKEN");
        ItemDto itemdto = new ItemDto(name, 1, price);

            Cart cart = webClient.post()
                    .uri("/cart/user/items")
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                    .bodyValue(itemdto)
                    .retrieve()
                    .bodyToMono(Cart.class)
                    .block();

        // 4. Update session counter
        int totalItems = cart.getItems().stream()
                .mapToInt(CartItem::getQuantity)
                .sum();
        request.getSession().setAttribute("cartCount", totalItems);


        return ResponseEntity.ok(cart);

    }
}
