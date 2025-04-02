package com.example.cart_service.service;

import com.example.cart_service.dto.CartItemDTO;
import com.example.cart_service.entity.Cart;
import com.example.cart_service.entity.CartItem;
import com.example.cart_service.repository.CartRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;

@Service
public class CartService {

    private final CartRepository cartRepository;
    public CartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    public Cart getOrCreateCartForUser(Long userId) {
        Cart cart = cartRepository.getCartByUserId(userId);
        if (cart == null) {
            cart = new Cart();
            cart.setUserId(userId);
            cart.setItems(new ArrayList<>());
            cart = cartRepository.save(cart); // persist right away
        }
        return cart;
    }


    public Cart addItemToCart(Long userId, CartItemDTO itemDTO){
        Cart cart = getOrCreateCartForUser(userId);

        // Create a new cart item using data from the DTO
        CartItem newItem = new CartItem();
        newItem.setProductName(itemDTO.getProductName());
        newItem.setQuantity(itemDTO.getQuantity());
        newItem.setUnitPrice(itemDTO.getUnitPrice());
        newItem.setCart(cart);
        // Optionally, if you have a field for item total
        BigDecimal itemTotal = itemDTO.getUnitPrice().multiply(BigDecimal.valueOf(itemDTO.getQuantity()));

        // Add the new item to the cart
        cart.getItems().add(newItem);
        System.out.println("Saving cart with userId: " + cart.getUserId());
        return cartRepository.save(cart);
    }

    public Cart updateItemInCart(Long userId, Long itemId, CartItemDTO cartItemDTO){

        Cart cart = getOrCreateCartForUser(userId);
        for(CartItem cartItem : cart.getItems()){
            if(cartItem.getId().equals(itemId)){
                cartItem.setQuantity(cartItemDTO.getQuantity());
                cartItem.setUnitPrice(cartItemDTO.getUnitPrice());
                cartItem.setProductName(cartItemDTO.getProductName());
                cartItem.setProductId(cartItemDTO.getProductId());
                break;
            }
        }
        return cartRepository.save(cart);
    }

    public Cart removeItemFromCart(Long userId, Long itemId){
        Cart cart = getOrCreateCartForUser(userId);

        cart.getItems().removeIf(cartItem -> cartItem.getId().equals(itemId));
        return cartRepository.save(cart);
    }

    public void clearCart(Long userId) {
        Cart cart = getOrCreateCartForUser(userId);
        cart.getItems().clear();
        cartRepository.save(cart);
    }


}
