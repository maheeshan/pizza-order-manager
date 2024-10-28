package com.san.adesso.pizza_order_manager.rabbit;

import com.san.adesso.pizza_order_manager.config.RabbitConfigProperties;
import com.san.adesso.pizza_order_manager.entity.OrderMessage;
import com.san.adesso.pizza_order_manager.entity.OrderStatus;
import com.san.adesso.pizza_order_manager.repository.OrderRepository;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderConsumerTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private RabbitConfigProperties rabbitConfigProperties;

    @InjectMocks
    private OrderConsumer orderConsumer;

    EasyRandom generator = new EasyRandom();

    @Test
    void processNewOrder() {

        var orderMessage = generator.nextObject(OrderMessage.class);

        orderConsumer.processNewOrder(orderMessage);

        verify(orderRepository, times(1)).save(argThat(order ->
                order.getCode().equals(orderMessage.getCode()) &&
                        order.getStatus() == OrderStatus.PENDING &&
                        order.getItems().equals(orderMessage.getItems())
        ));
    }
}