package com.san.adesso.pizza_order_manager.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "PIZZA_ORDER")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_seq_gen")
    @SequenceGenerator(name = "order_seq_gen", sequenceName = "order_sequence", initialValue = 1000, allocationSize = 1)
    private Long id;
    @Column(unique = true, updatable = false)
    private String code;
    @ElementCollection
    @CollectionTable(
            name = "ORDER_ITEM",
            joinColumns = @JoinColumn(name = "ORDER_ID")
    )
    private List<OrderItem> items;
    private OrderStatus status;
    private LocalDateTime dateTimeCreated;
    private LocalDateTime dateTimeUpdated;

    public Order() {
        this.dateTimeCreated = LocalDateTime.now();
        this.status = OrderStatus.PENDING;
    }
}
