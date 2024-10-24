package com.san.adesso.pizza_order_manager.controller;

import com.san.adesso.pizza_order_manager.entity.OrderDTO;
import com.san.adesso.pizza_order_manager.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping("pending")
    public List<OrderDTO> getPendingOrders() {
        return orderService.getPendingOrders();
    }

    @PostMapping("add")
    public String addOrder(@RequestBody OrderDTO orderDTO) {
        return orderService.addOrder(orderDTO);
    }
}
