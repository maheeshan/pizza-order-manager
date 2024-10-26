package com.san.adesso.pizza_order_manager.service;

import com.san.adesso.pizza_order_manager.config.RabbitConfigProperties;
import com.san.adesso.pizza_order_manager.entity.OrderDTO;
import com.san.adesso.pizza_order_manager.entity.OrderMessage;
import com.san.adesso.pizza_order_manager.entity.OrderStatus;
import com.san.adesso.pizza_order_manager.mapper.OrderMapper;
import com.san.adesso.pizza_order_manager.repository.OrderRepository;
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

    public void sendOrderStatusUpdate(OrderMessage message) {
        rabbitTemplate.convertAndSend(rabbitConfigProperties.getOrderExchange(),
                rabbitConfigProperties.getRoutingKeyStatus(),
                message
        );
    }

    private String generateUniqueCode() {
        return UUID.randomUUID().toString();
    }
}
