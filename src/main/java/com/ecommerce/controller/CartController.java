package com.ecommerce.controller;

import com.ecommerce.dto.CartItemDTO;
import com.ecommerce.dto.CartResponseDTO;
import com.ecommerce.dto.CartResponseDTO.CartItemSummary;
import com.ecommerce.model.Cart;
import com.ecommerce.security.CustomUserDetails;
import com.ecommerce.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
@CrossOrigin(origins = "*")
public class CartController {

    @Autowired
    private CartService cartService;
    
    // customer : add item to cart
    @PostMapping("/add")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<String> addToCart(@RequestBody CartItemDTO cartItemDTO, Authentication authentication) {
        Long userId = ((CustomUserDetails) authentication.getPrincipal()).getId();
        cartService.addToCart(userId, cartItemDTO.getProductId(), cartItemDTO.getQuantity());
        return ResponseEntity.ok("Item added to cart");
    }
    
    // customer : check cart-item
    @GetMapping("/my-cart")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<CartResponseDTO> viewMyCart(Authentication authentication) {
        Long userId = ((CustomUserDetails) authentication.getPrincipal()).getId();
        Cart cart = cartService.getCartByUserId(userId);
        return ResponseEntity.ok(convertToDTO(cart));
    }
    
    // not in use (not necessary)
    @GetMapping("/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CartResponseDTO> viewUserCart(@PathVariable Long userId) {
        Cart cart = cartService.getCartByUserId(userId);
        return ResponseEntity.ok(convertToDTO(cart));
    }
    
    // customer : clear cart
    @DeleteMapping("/clear")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<String> clearCart(Authentication authentication) {
        Long userId = ((CustomUserDetails) authentication.getPrincipal()).getId();
        cartService.clearCart(userId);
        return ResponseEntity.ok("Cart cleared");
    }
    
    // customer : update item in cart
    @PutMapping("/update")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<CartResponseDTO> updateQuantity(@RequestParam Long productId,
                                                          @RequestParam int quantity,
                                                          Authentication authentication) {
        Long userId = ((CustomUserDetails) authentication.getPrincipal()).getId();
        Cart updatedCart = cartService.updateCartItemQuantity(userId, productId, quantity);
        return ResponseEntity.ok(convertToDTO(updatedCart));
    }
    
    // customer : delete item from cart
    @DeleteMapping("/remove")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<CartResponseDTO> removeItem(@RequestParam Long productId,
                                                      Authentication authentication) {
        Long userId = ((CustomUserDetails) authentication.getPrincipal()).getId();
        Cart updatedCart = cartService.removeItemFromCart(userId, productId);
        return ResponseEntity.ok(convertToDTO(updatedCart));
    }

    private CartResponseDTO convertToDTO(Cart cart) {
        List<CartItemSummary> itemSummaries = cart.getCartItems().stream().map(item ->
                new CartItemSummary(
                        item.getProduct().getId(),
                        item.getProduct().getName(),
                        item.getQuantity()
                )
        ).toList();

        return new CartResponseDTO(cart.getId(), cart.getTotal(), itemSummaries);
    }
}
