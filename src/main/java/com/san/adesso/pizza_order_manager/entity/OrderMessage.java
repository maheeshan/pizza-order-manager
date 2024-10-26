package com.san.adesso.pizza_order_manager.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderMessage {

    private String code;
    private OrderStatus status;
    private List<OrderItem> items;

    public OrderMessage(String code, OrderStatus status) {
        this.code = code;
        this.status = status;
    }
}
