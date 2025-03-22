package com.order_service.service;

import com.order_service.Enum.OrderStatus;
import com.order_service.dto.OrderStatusDto;
import com.order_service.entity.Order;
import com.order_service.entity.OrderItem;
import com.order_service.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id).orElse(null);
    }

    public Order createOrder(Order order) {

        order.setOrderDateTime(LocalDateTime.now());
        order.setStatus(OrderStatus.PENDING);

        // total amount of order Price--
        List<OrderItem> orderItems = order.getOrderItems();

        BigDecimal total = BigDecimal.ZERO;

        for (OrderItem orderItem : orderItems) {

            BigDecimal itemTotal = orderItem.getUnitPrice()
                    .multiply(BigDecimal.valueOf(orderItem.getQuantity()));

            total = total.add(itemTotal);

        }
        order.setTotalAmount(total);


        order.getOrderItems().forEach(item -> {item.setOrder(order);});
        return orderRepository.save(order);
    }

    public List<Order> orderByUserId(Long userId) {
        return orderRepository.findByUserId(userId);
    }

    public Order updateOrder(Order order) {
        return orderRepository.save(order);
    }

    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }

    public void updateOrderStatus(Long id, OrderStatusDto status) {
        Order orderById = orderRepository.findById(id).orElse(null);

            orderById.setStatus(status.getStatus());
            orderRepository.save(orderById);

    }

}
