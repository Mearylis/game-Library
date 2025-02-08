package com.gamelibrary.controllers;

import com.gamelibrary.models.Order;
import com.gamelibrary.services.OrderService;

import java.util.Optional;

public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    public Optional<Order> getFullOrderDescription(int orderId) {
        return orderService.getFullOrderDescription(orderId);
    }
}