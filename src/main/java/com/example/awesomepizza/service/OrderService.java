package com.example.awesomepizza.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.awesomepizza.model.Order;
import com.example.awesomepizza.model.OrderStatus;
import com.example.awesomepizza.repository.OrderRepository;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    public Order createOrder(String pizzaDetails, String customerName) {
        Order order = new Order(pizzaDetails, customerName);
        return orderRepository.save(order);
    }

    public List<Order> getOrdersInQueue() {
        return orderRepository.findByStatus(OrderStatus.IN_QUEUE);
    }

    public Order getOrderByCode(String orderCode) {
        return orderRepository.findByOrderCode(orderCode)
                .orElseThrow(() -> new NoSuchElementException("Order not found with code: " + orderCode));
    }

    public Order updateOrderStatus(Long orderId, String status){
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new NoSuchElementException("Order not found"));
        OrderStatus orderStatus = OrderStatus.valueOf(status);
        order.setStatus(orderStatus);
        return orderRepository.save(order);
    }
}
