package com.order_service.controller;

import com.order_service.Enum.OrderStatus;
import com.order_service.dto.OrderStatusDto;
import com.order_service.entity.Order;
import com.order_service.service.OrderService;
import com.order_service.util.JwtUtil;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;
    private final JwtUtil jwtUtil;

    public OrderController(OrderService orderService, JwtUtil jwtUtil) {

        this.orderService = orderService;
        this.jwtUtil = jwtUtil;
    }

    @GetMapping("/manager")
    public Order getOrder(@RequestHeader("Authorization") String token) {

        Long id = jwtUtil.extractUserId(token);

        return orderService.getOrderById(id);
    }

    @PostMapping("/create")
    public Order createOrder(@RequestHeader("Authorization") String token,
                             @RequestBody Order order) {

        return orderService.createOrder(order);
    }

    @GetMapping("/user")
    public List<Order> ordersByUserId(@RequestHeader("Authorization") String token) {

        Long userId = jwtUtil.extractUserId(token);

        return orderService.orderByUserId(userId);
    }

    @PutMapping
    public Order updateOrder(@RequestHeader("Authorization") String token,
            @RequestBody Order order) {

        return orderService.updateOrder(order);
    }

    @DeleteMapping("/{id}")
    public String deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return "Deleted an Order!";

    }

    @PutMapping("/{id}")
    public String updateOrderStatus(@PathVariable Long id, @RequestBody OrderStatusDto status) {

        orderService.updateOrderStatus(id, status);

        return "Updated an Order's status!";

    }
}
