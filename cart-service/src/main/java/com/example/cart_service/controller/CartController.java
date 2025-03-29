package com.example.cart_service.controller;

import com.example.cart_service.dto.CartItemDTO;
import com.example.cart_service.entity.Cart;
import com.example.cart_service.service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<Cart> getCart(@PathVariable Long userId) {
        Cart cart = cartService.getCartForUser(userId);
        return ResponseEntity.ok(cart);
    }

    @PostMapping("/user/{userId}/items")
    public ResponseEntity<Cart> addItemToCart(
            @PathVariable Long userId,
            @RequestBody CartItemDTO itemDTO
            ){
        Cart cart = cartService.addItemToCart(userId, itemDTO);
        return ResponseEntity.ok(cart);
    }

    //Update the item in cart
    @PostMapping("/user/{userId}/items/{itemId}")
    public ResponseEntity<Cart> updateItemInCart(
            @PathVariable Long userId, @RequestBody CartItemDTO itemDTO, @PathVariable Long itemId
    ){
        Cart updatedCart = cartService.updateItemInCart(userId, itemId, itemDTO);
        return ResponseEntity.ok(updatedCart);
    }


    @DeleteMapping("/user/{userId}/items/{itemId}")
    public ResponseEntity<Cart> deleteItemFromCart(
            @PathVariable Long userId, @PathVariable Long itemId
    ){
        Cart cart = cartService.removeItemFromCart(userId, itemId);
        return ResponseEntity.ok(cart);
    }

    @DeleteMapping("/user/{userId}")
    public ResponseEntity<Cart> clearCart(
            @PathVariable Long userId
    ){
        cartService.clearCart(userId);
        return ResponseEntity.noContent().build();

    }

}
