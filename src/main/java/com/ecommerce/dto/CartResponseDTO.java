package com.ecommerce.dto;

import java.util.List;

public class CartResponseDTO {
    private Long cartId;
    private Double total;
    private List<CartItemSummary> items;

    public CartResponseDTO(Long cartId, Double total, List<CartItemSummary> items) {
        this.cartId = cartId;
        this.total = total;
        this.items = items;
    }

    public Long getCartId() {
        return cartId;
    }

    public Double getTotal() {
        return total;
    }

    public List<CartItemSummary> getItems() {
        return items;
    }

    public static class CartItemSummary {
        private Long productId;
        private String productName;
        private int quantity;

        public CartItemSummary(Long productId, String productName, int quantity) {
            this.productId = productId;
            this.productName = productName;
            this.quantity = quantity;
        }

        public Long getProductId() {
            return productId;
        }

        public String getProductName() {
            return productName;
        }

        public int getQuantity() {
            return quantity;
        }
    }
}

