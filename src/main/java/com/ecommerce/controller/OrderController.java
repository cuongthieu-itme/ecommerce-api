package com.ecommerce.controller;

import com.ecommerce.dto.OrderDTO;
import com.ecommerce.model.Order;
import com.ecommerce.service.OrderService;
import com.ecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    // customer: Create order using logged-in user info
    @PreAuthorize("hasRole('CUSTOMER')")
    @PostMapping("/create")
    public ResponseEntity<String> createOrder(Principal principal) {
        Long userId = userService.getUserIdFromPrincipal(principal);
        orderService.createOrder(userId);
        return ResponseEntity.ok("✅ Order placed successfully.");
    }

    // customer: Get their own orders
    @PreAuthorize("hasRole('CUSTOMER')")
    @GetMapping("/my")
    public ResponseEntity<List<OrderDTO>> getOrders(Principal principal) {
        Long userId = userService.getUserIdFromPrincipal(principal);
        List<Order> orderList = orderService.getOrders(userId);

        List<OrderDTO> orders = orderList.stream().map(order -> {
            OrderDTO dto = new OrderDTO();
            dto.setId(order.getId());
            dto.setOrderDate(order.getOrderDate());
            dto.setTotalAmount(order.getTotalAmount());
            dto.setUserId(order.getUser().getId());
            return dto;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(orders);
    }

    // admin: can see all orders which are placed
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/all")
    public ResponseEntity<List<OrderDTO>> getAllOrders() {
        List<Order> orderList = orderService.getAllOrders();
        List<OrderDTO> orders = orderList.stream().map(order -> {
            OrderDTO dto = new OrderDTO();
            dto.setId(order.getId());
            dto.setOrderDate(order.getOrderDate());
            dto.setTotalAmount(order.getTotalAmount());
            dto.setUserId(order.getUser().getId());
            return dto;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(orders);
    }
}
