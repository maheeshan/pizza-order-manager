package com.san.adesso.pizza_order_manager.entity;

public enum OrderStatus {
    PENDING("PENDING"), IN_PROGRESS("IN PROGRESS"), COMPLETED("COMPLETED");

    public final String label;

    OrderStatus(String label) {
        this.label = label;
    }
}
