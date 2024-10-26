package com.san.adesso.pizza_order_manager.controller;

import com.san.adesso.pizza_order_manager.entity.OrderDTO;
import com.san.adesso.pizza_order_manager.entity.OrderStatus;
import com.san.adesso.pizza_order_manager.entity.OrderMessage;
import com.san.adesso.pizza_order_manager.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/pending")
    public ResponseEntity<List<OrderDTO>> getPendingOrders() {
        return ResponseEntity.ok(orderService.getPendingOrders());
    }

    @PostMapping("/place")
    public ResponseEntity<String> placeOrder(@RequestBody OrderDTO order) {
        try {
            orderService.placeOrder(order);
            return ResponseEntity.status(HttpStatus.CREATED).body("Order placed successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to place order");
        }
    }

    @PatchMapping("/{orderId}/status")
    public ResponseEntity<String> updateOrderStatus(
            @PathVariable String code,
            @RequestParam OrderStatus status
    ) {
        try {
            OrderMessage statusMessage = new OrderMessage(code, status);
            orderService.sendOrderStatusUpdate(statusMessage);
            return ResponseEntity.ok("Order status updated to " + status);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update order status");
        }
    }
}
