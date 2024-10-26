package com.san.adesso.pizza_order_manager.entity;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Embeddable
@Data
public class OrderItem {
    String pizzaType;
    int quantity;
}
