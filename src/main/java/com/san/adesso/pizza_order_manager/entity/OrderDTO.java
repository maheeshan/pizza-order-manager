package com.san.adesso.pizza_order_manager.entity;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public class OrderDTO {
    private String code;
    private String pizzaType;
    private OrderStatus status;
    private LocalDateTime dateTimeCreated;
    private LocalDateTime dateTimeUpdated;
}
