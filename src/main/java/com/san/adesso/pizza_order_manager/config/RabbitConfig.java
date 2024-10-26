package com.san.adesso.pizza_order_manager.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class RabbitConfig {

    private final RabbitConfigProperties rabbitConfigProperties;

    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        return new Jackson2JsonMessageConverter(objectMapper);
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jackson2JsonMessageConverter());
        return rabbitTemplate;
    }

    @Bean
    public Queue orderQueue() {
        return new Queue(rabbitConfigProperties.getQueueOrderNew(), false);
    }

    @Bean
    public Queue orderStatusQueue() {
        return new Queue(rabbitConfigProperties.getQueueOrderStatus(), false);
    }

    @Bean
    public TopicExchange orderExchange() {
        return new TopicExchange(rabbitConfigProperties.getOrderExchange());
    }

    @Bean
    public Binding orderQueueBinding(Queue orderQueue, TopicExchange orderExchange) {
        return BindingBuilder.bind(orderQueue).to(orderExchange).with(rabbitConfigProperties.getRoutingKeyNewOrder());
    }

    @Bean
    public Binding orderStatusQueueBinding(Queue orderStatusQueue, TopicExchange orderExchange) {
        return BindingBuilder.bind(orderStatusQueue).to(orderExchange).with(rabbitConfigProperties.getRoutingKeyStatus());
    }
}
