package com.example.cart_service.service;

import com.example.cart_service.entity.Cart;
import com.example.cart_service.repository.CartRepository;
import org.springframework.stereotype.Service;

@Service
public class CartService {

    private final CartRepository cartRepository;
    public CartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }
    public Cart getCart(Long id) {
        return cartRepository.getCartsById(id);
    }
    public Cart updateCart(Cart cart) {
        return cartRepository.save(cart);
    }
    public void deleteCart(Long id) {
        cartRepository.deleteById(id);
    }


}
