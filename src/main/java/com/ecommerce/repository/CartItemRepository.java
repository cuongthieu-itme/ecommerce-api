package com.ecommerce.repository;

import com.ecommerce.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    
    // Find all items in a given cart
    List<CartItem> findByCartId(Long cartId);
    
    // Find specific item by cart and product (used for update quantity or check if item already exists)
    Optional<CartItem> findByCartIdAndProductId(Long cartId, Long productId);

    // Optional: delete all items for a cart (e.g. on checkout)
    void deleteByCartId(Long cartId);
}

