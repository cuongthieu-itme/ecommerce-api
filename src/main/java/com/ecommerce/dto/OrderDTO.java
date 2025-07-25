package com.ecommerce.dto;

import java.util.Date;

public class OrderDTO {
    private Long id;
    private Date orderDate;
    private double totalAmount;
    private Long userId;

    // Getters
    public Long getId() {
        return id;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public Long getUserId() {
        return userId;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
