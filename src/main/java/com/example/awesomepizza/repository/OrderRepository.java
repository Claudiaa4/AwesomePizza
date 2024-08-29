package com.example.awesomepizza.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.awesomepizza.model.Order;
import com.example.awesomepizza.model.OrderStatus;

import java.util.List;
import java.util.Optional;


public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByStatus(OrderStatus status);
    Optional<Order> findByOrderCode(String code);
}
