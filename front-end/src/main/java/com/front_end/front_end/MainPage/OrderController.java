package com.front_end.front_end.MainPage;

import com.front_end.front_end.entities.Cart;
import com.front_end.front_end.entities.Order;
import com.front_end.front_end.entities.OrderItem;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class OrderController {

    private final WebClient webClient;

    public OrderController(WebClient webClient) {
        this.webClient = WebClient.builder().baseUrl("http://localhost:8086").build();
    }

    @PostMapping("/createOrder")
    public String createOrder(HttpServletRequest request, Model model) {

        String token = (String) request.getSession().getAttribute("JWT_TOKEN");

        Cart cart = webClient.get()
                .uri("/cart/user")
                .header(HttpHeaders.AUTHORIZATION, "Bearer "+token)
                .retrieve()
                .bodyToMono(Cart.class)
                .block();

        if (cart == null) {
            // no cart exists; redirect or show error accordingly
            return "redirect:/cart";
        }

        Order order = new Order();
        order.setUserId(cart.getUserId());

        List<OrderItem> orderItems = cart.getItems().stream().map(
                cartItem -> {
                    OrderItem oi = new OrderItem();
                    oi.setProductId(cartItem.getProductId());
                    oi.setProductName(cartItem.getProductName());
                    oi.setQuantity(cartItem.getQuantity());
                    oi.setUnitPrice(cartItem.getUnitPrice());

                    return oi;
                }
        ).collect(Collectors.toList());
        order.setOrderItems(orderItems);

        Order createdOrder = webClient.post()
                .uri("/order/create")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .bodyValue(order)
                .retrieve()
                .bodyToMono(Order.class)
                .block();

        request.getSession().setAttribute("recentOrder", createdOrder);

        return "redirect:/orderPage";
    }

    @GetMapping("/orderPage")
    public String orderPage(HttpServletRequest request, Model model) {

        Order recentOrder = (Order) request.getSession().getAttribute("recentOrder");
        model.addAttribute("recentOrder", recentOrder);

        return "orderConfirmation";
    }

}
