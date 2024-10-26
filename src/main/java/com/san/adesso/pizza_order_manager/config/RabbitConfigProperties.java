package com.san.adesso.pizza_order_manager.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "rabbitmq")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RabbitConfigProperties {

    private String orderExchange;
    private String queueOrderNew;
    private String queueOrderStatus;
    private String routingKeyNewOrder;
    private String routingKeyStatus;
}
