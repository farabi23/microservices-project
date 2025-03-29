package com.example.cart_service.repository;

import com.example.cart_service.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart getCartsById(Long id);
    Cart getCartByUserId(Long userId);
}
