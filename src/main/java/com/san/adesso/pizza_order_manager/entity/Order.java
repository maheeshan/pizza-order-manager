package com.san.adesso.pizza_order_manager.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, updatable = false)
    private String code;
    private String pizzaType;
    private OrderStatus status;
    private LocalDateTime dateTimeCreated;
    private LocalDateTime dateTimeUpdated;

    public Order() {
        this.dateTimeCreated = LocalDateTime.now();
        this.code = generateUniqueCode();
        this.status = OrderStatus.PENDING;
    }

    private String generateUniqueCode() {
        return UUID.randomUUID().toString();
    }
}
