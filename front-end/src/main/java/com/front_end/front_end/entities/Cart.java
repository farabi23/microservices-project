package com.front_end.front_end.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Cart {


    private Long id;


    private Long userId;


    private List<CartItem> items = new ArrayList<>();

    public Cart() {}

    public Cart(Long id, Long userId, List<CartItem> items) {
        this.id = id;
        this.userId = userId;
        this.items = items;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<CartItem> getItems() {
        return items;
    }

    public void setItems(List<CartItem> items) {
        this.items = items;
    }

    public int getTotalItems() {
        return items.stream()
                .mapToInt(CartItem::getQuantity)
                .sum();
    }

    public BigDecimal getTotalPrice() {
        return items.stream()
                .map(item -> item.getUnitPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

}
