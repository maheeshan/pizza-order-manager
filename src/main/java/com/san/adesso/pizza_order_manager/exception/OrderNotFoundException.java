package com.san.adesso.pizza_order_manager.exception;

public class OrderNotFoundException extends RuntimeException {
    public OrderNotFoundException(String code) {
        super(String.format("Order: %s is not found ", code));
    }
}
