package com.front_end.front_end.MainPage;

import com.front_end.front_end.entities.Cart;
import com.front_end.front_end.entities.CartItem;
import com.front_end.front_end.utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

@Controller
public class CartController {

    private final WebClient webClient;
    private final JwtUtil jwtUtil;

    public CartController(WebClient webClient, JwtUtil jwtUtil) {
        this.webClient = WebClient.builder().baseUrl("http://localhost:8086").build();
        this.jwtUtil = jwtUtil;
    }


    @GetMapping("/cart")
    public String cart(Model model, HttpServletRequest request) {

        String token = (String) request.getSession().getAttribute("JWT_TOKEN");

        model.addAttribute("jwtToken", token);

        Cart cart = webClient.get()
                .uri("/cart/user")
                .header(HttpHeaders.AUTHORIZATION, "Bearer "+token)
                .retrieve()
                .bodyToMono(Cart.class)
                .block();

//        Map<String, CartItem> groupedItems = new LinkedHashMap<>();
//        for (CartItem item : cart.getItems()) {
//            if (groupedItems.containsKey(item.getProductName())) {
//                CartItem existing = groupedItems.get(item.getProductName());
//                existing.setQuantity(existing.getQuantity() + item.getQuantity());
//                // Keep unit price as-is
//            } else {
//                groupedItems.put(item.getProductName(), new CartItem(
//                        item.getProductName(),
//                        item.getQuantity(),
//                        item.getUnitPrice()
//                ));
//            }
//        }

        model.addAttribute("cart", cart);

        return "cartpage";

    }

    @PostMapping("/cart/delete")
    public String deleteItemFromCart(
            @RequestParam Long itemId,
            HttpServletRequest request,
            Model model) {

        String token = (String) request.getSession().getAttribute("JWT_TOKEN");
        model.addAttribute("jwtToken", token);

        Cart updatedCart = webClient
                .delete()
                .uri("/cart/user/items/" + itemId)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .retrieve()
                .bodyToMono(Cart.class)
                .block();

        model.addAttribute("cart", updatedCart);
        model.addAttribute("cartCount", updatedCart.getItems().stream()
                .mapToInt(CartItem::getQuantity).sum());

        return "redirect:/cart"; // or redirect to /cart
    }




}
