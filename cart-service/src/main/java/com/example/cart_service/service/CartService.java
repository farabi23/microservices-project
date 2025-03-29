package com.example.cart_service.service;

import com.example.cart_service.dto.CartItemDTO;
import com.example.cart_service.entity.Cart;
import com.example.cart_service.entity.CartItem;
import com.example.cart_service.repository.CartRepository;
import org.springframework.stereotype.Service;

@Service
public class CartService {

    private final CartRepository cartRepository;
    public CartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    public Cart getCartForUser(Long userId) {
        return cartRepository.getCartByUserId(userId);
    }


    public Cart createCartForUser(Long userId){
        Cart newCart = new Cart();
        return cartRepository.save(newCart);
    }

    public Cart updateCart(Cart cart) {
        return cartRepository.save(cart);
    }
    public void deleteCart(Long id) {
        cartRepository.deleteById(id);
    }

    public Cart addItemToCart(Long userId, CartItemDTO cartItemDTO){
        Cart cart = getCartForUser(userId);
        // adding cartItem to cart using dto
        CartItem item = new CartItem();
        item.setProductId(cartItemDTO.getProductId());
        item.setProductName(cartItemDTO.getProductName());
        item.setQuantity(cartItemDTO.getQuantity());
        item.setUnitPrice(cartItemDTO.getUnitPrice());
        item.setCart(cart);
        cart.getItems().add(item);
        return cart;
    }

    public Cart updateItemInCart(Long userId, Long itemId, CartItemDTO cartItemDTO){

        Cart cart = getCartForUser(userId);
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
        Cart cart = getCartForUser(userId);

        cart.getItems().removeIf(cartItem -> cartItem.getId().equals(itemId));
        return cartRepository.save(cart);
    }

    public void clearCart(Long userId) {
        Cart cart = getCartForUser(userId);
        cart.getItems().clear();
        cartRepository.save(cart);
    }


}
