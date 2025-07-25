package com.ecommerce.repository;

import com.ecommerce.model.Order;
import com.ecommerce.model.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUser(User user);
}

