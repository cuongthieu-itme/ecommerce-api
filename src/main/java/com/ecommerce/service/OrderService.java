package com.ecommerce.service;

import com.ecommerce.model.*;
import com.ecommerce.repository.OrderRepository;
import com.ecommerce.repository.ProductRepository;
import com.ecommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;


 //  Create Order from Cart with stock update
    public Order createOrder(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Cart cart = user.getCart();

        if (cart == null || cart.getCartItems().isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        Order order = new Order();
        order.setOrderDate(new Date());
        order.setUser(user);

        double total = 0.0;
        List<OrderItem> orderItems = new ArrayList<>();

        for (CartItem cartItem : cart.getCartItems()) {
            Product product = cartItem.getProduct();
            int requestedQty = cartItem.getQuantity();

            if (product == null) {
                throw new RuntimeException("Product not found in cart");
            }

            // Check if product has enough stock
            if (product.getStock() < requestedQty) {
                throw new RuntimeException("Insufficient stock for product: " + product.getName());
            }

            //  Deduct stock
            product.setStock(product.getStock() - requestedQty);
            productRepository.save(product);

            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(product);
            orderItem.setQuantity(requestedQty);
            orderItem.setPrice(product.getPrice());
            orderItem.setOrder(order);

            orderItems.add(orderItem);
            total += product.getPrice() * requestedQty;
        }

        order.setOrderItems(orderItems);
        order.setTotalAmount(total);

        cart.getCartItems().clear(); // ✅ Clear cart after successful order

        return orderRepository.save(order);
    }


    //  Get all orders for a user
    public List<Order> getOrders(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        return orderRepository.findByUser(user);
    }

    //  ADMIN: Get all orders
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
}





