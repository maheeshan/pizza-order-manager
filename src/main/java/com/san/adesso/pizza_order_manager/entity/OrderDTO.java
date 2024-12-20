package com.san.adesso.pizza_order_manager.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class OrderDTO {
    private String code;
    private List<OrderItem> items;
    private OrderStatus status;
    private LocalDateTime dateTimeCreated;
    private LocalDateTime dateTimeUpdated;
}
