package com.san.adesso.pizza_order_manager.service;

import com.san.adesso.pizza_order_manager.config.RabbitConfigProperties;
import com.san.adesso.pizza_order_manager.entity.Order;
import com.san.adesso.pizza_order_manager.entity.OrderDTO;
import com.san.adesso.pizza_order_manager.entity.OrderMessage;
import com.san.adesso.pizza_order_manager.entity.OrderStatus;
import com.san.adesso.pizza_order_manager.exception.OrderNotFoundException;
import com.san.adesso.pizza_order_manager.mapper.OrderMapper;
import com.san.adesso.pizza_order_manager.repository.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final RabbitTemplate rabbitTemplate;
    private final OrderMapper orderMapper;
    private final RabbitConfigProperties rabbitConfigProperties;

    @Transactional
    public List<OrderDTO> getPendingOrders() {
        var orders = orderRepository.findByStatus(OrderStatus.PENDING);
        return orders.stream().map(orderMapper::toOrderDTO).toList();
    }

    public void placeOrder(OrderDTO orderDTO) {
        OrderMessage orderMessage = new OrderMessage(generateUniqueCode(), OrderStatus.PENDING, orderDTO.getItems());
        rabbitTemplate.convertAndSend(
                rabbitConfigProperties.getOrderExchange(),
                rabbitConfigProperties.getRoutingKeyNewOrder(),
                orderMessage
        );
    }

    @Transactional
    public void updateOrderStatus(String code, OrderStatus status) {
        orderRepository.findByCode(code)
                .ifPresentOrElse(order -> order.setStatus(status), () -> {
                    throw new OrderNotFoundException(code);
                });
    }

    @Transactional
    public OrderStatus getOrderStatus(String code) {
        return orderRepository.findByCode(code)
                .map(Order::getStatus)
                .orElseThrow(() -> new OrderNotFoundException(code));
    }

    private String generateUniqueCode() {
        return "ORD" + UUID.randomUUID().toString().replaceAll("-", "").substring(0, 8);
    }
}
