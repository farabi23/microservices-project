package com.order_service.controller;

import com.order_service.Enum.OrderStatus;
import com.order_service.dto.OrderStatusDto;
import com.order_service.entity.Order;
import com.order_service.service.OrderService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/{id}")
    public Order getOrder(@PathVariable Long id) {
        return orderService.getOrderById(id);
    }

    @PostMapping
    public Order createOrder(@RequestBody Order order) {
        return orderService.createOrder(order);
    }

    @GetMapping("/user/{userId}")
    public List<Order> ordersByUserId(@PathVariable Long userId) {
        return orderService.orderByUserId(userId);
    }

    @PutMapping
    public Order updateOrder(@RequestBody Order order) {
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
