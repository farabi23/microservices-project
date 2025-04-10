package com.example.cart_service.controller;

import com.example.cart_service.dto.CartItemDTO;
import com.example.cart_service.entity.Cart;
import com.example.cart_service.entity.CartItem;
import com.example.cart_service.service.CartService;
import com.example.cart_service.util.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;
    private final JwtUtil jwtUtil;

    public CartController(CartService cartService, JwtUtil jwtUtil) {
        this.cartService = cartService;
        this.jwtUtil = jwtUtil;
    }

    @GetMapping("/user")
    public ResponseEntity<Cart> getCart(@RequestHeader("Authorization") String token) {

        Long userId = jwtUtil.extractUserId(token.substring(7));

        Cart cart = cartService.getOrCreateCartForUser(userId);
        return ResponseEntity.ok(cart);
    }

    @PostMapping("/user/items")
    public ResponseEntity<Cart> addItemToCart(
            @RequestHeader("Authorization") String token,
            @RequestBody CartItemDTO itemDTO
    ){
        System.out.println(">>> Request reached controller !");
        Long userId = jwtUtil.extractUserId(token.substring(7));

        Cart cart = cartService.getOrCreateCartForUser(userId);
        cart = cartService.addItemToCart(userId, itemDTO);

        //Cart cart = cartService.addItemToCart(userId, itemDTO);
        return ResponseEntity.ok(cart);
    }

    //Update the item in cart
    @PostMapping("/user/items/{itemId}")
    public ResponseEntity<Cart> updateItemInCart(
            @RequestHeader("Authorization") String token,
            @RequestBody CartItemDTO itemDTO, @PathVariable Long itemId
    ){
        Long userId = jwtUtil.extractUserId(token.substring(7));
        Cart updatedCart = cartService.updateItemInCart(userId, itemId, itemDTO);
        return ResponseEntity.ok(updatedCart);
    }


    @DeleteMapping("/user/items/{itemId}")
    public ResponseEntity<Cart> deleteItemFromCart(
            @RequestHeader("Authorization") String token, @PathVariable Long itemId
    ){
        System.out.println(">>> DELETE request received for itemId: " + itemId);
        System.out.println(">>> Full Authorization header: " + token);

        Long userId = jwtUtil.extractUserId(token.substring(7));
        System.out.println(">>> Extracted userId: " + userId);

        Cart cart = cartService.removeItemFromCart(userId, itemId);

        System.out.println(">>> Returning cart with " + cart.getItems().size() + " items");
        return ResponseEntity.ok(cart);
    }

    @DeleteMapping("/user")
    public ResponseEntity<Cart> clearCart(
            @RequestHeader("Authorization") String token
    ){
        Long userId = jwtUtil.extractUserId(token.substring(7));

        cartService.clearCart(userId);
        return ResponseEntity.noContent().build();

    }


    @PostMapping("/test")
    public String test(@RequestBody String body) {
        System.out.println(">>>>> TEST POST REACHED ✅");
        return "Test POST successful";
    }


}
