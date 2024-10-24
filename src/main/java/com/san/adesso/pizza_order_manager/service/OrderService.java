package com.san.adesso.pizza_order_manager.service;

import com.san.adesso.pizza_order_manager.entity.OrderDTO;
import com.san.adesso.pizza_order_manager.entity.OrderStatus;
import com.san.adesso.pizza_order_manager.mapper.OrderMapper;
import com.san.adesso.pizza_order_manager.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    public List<OrderDTO> getPendingOrders() {
        var orders = orderRepository.findByStatus(OrderStatus.PENDING);

        return orders.stream().map(orderMapper::toOrderDTO).toList();
    }

    public String addOrder(OrderDTO orderDTO) {
        var savedOrder = orderRepository.save(orderMapper.toOrder(orderDTO));
        return savedOrder.getCode();
    }
}
