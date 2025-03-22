package com.order_service.dto;

import com.order_service.Enum.OrderStatus;

public class OrderStatusDto {

    OrderStatus status;

    public OrderStatusDto() {}

    public OrderStatusDto(OrderStatus status) {
        this.status = status;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }
}
