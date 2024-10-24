package com.san.adesso.pizza_order_manager.config;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    @Value("${spring.rabbitmq.order-queue}")
    private String orderQueueName;

    public Queue orderQueue() {
        return new Queue(orderQueueName, false);
    }
}
