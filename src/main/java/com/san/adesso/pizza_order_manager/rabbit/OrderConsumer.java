package com.san.adesso.pizza_order_manager.rabbit;

import com.san.adesso.pizza_order_manager.config.RabbitConfigProperties;
import com.san.adesso.pizza_order_manager.entity.Order;
import com.san.adesso.pizza_order_manager.entity.OrderMessage;
import com.san.adesso.pizza_order_manager.entity.OrderStatus;
import com.san.adesso.pizza_order_manager.repository.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Transactional
public class OrderConsumer {

    private final OrderRepository orderRepository;
    private final RabbitConfigProperties rabbitConfigProperties;

    @RabbitListener(queues = "#{@rabbitConfigProperties.getQueueOrderNew()}")
    public void processNewOrder(OrderMessage orderMessage) {
        var orderEntity = new Order();
        orderEntity.setCode(orderMessage.getCode());
        orderEntity.setStatus(OrderStatus.PENDING);
        orderEntity.setItems(orderMessage.getItems());
        orderRepository.save(orderEntity);
    }
}
