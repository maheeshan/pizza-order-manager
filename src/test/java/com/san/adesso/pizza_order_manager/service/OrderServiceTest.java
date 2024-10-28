package com.san.adesso.pizza_order_manager.service;

import com.san.adesso.pizza_order_manager.config.RabbitConfigProperties;
import com.san.adesso.pizza_order_manager.entity.Order;
import com.san.adesso.pizza_order_manager.entity.OrderDTO;
import com.san.adesso.pizza_order_manager.entity.OrderMessage;
import com.san.adesso.pizza_order_manager.entity.OrderStatus;
import com.san.adesso.pizza_order_manager.exception.OrderNotFoundException;
import com.san.adesso.pizza_order_manager.mapper.OrderMapper;
import com.san.adesso.pizza_order_manager.mapper.OrderMapperImpl;
import com.san.adesso.pizza_order_manager.repository.OrderRepository;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    OrderRepository orderRepository;

    @Mock
    RabbitTemplate rabbitTemplate;

    @Mock
    RabbitConfigProperties rabbitConfigProperties;

    @InjectMocks
    OrderService orderService;

    @Spy
    OrderMapper orderMapper = new OrderMapperImpl();

    EasyRandom generator = new EasyRandom();

    @Test
    void getPendingOrders() {
        var orders = generator.objects(Order.class, 5).toList();
        when(orderRepository.findByStatus(OrderStatus.PENDING)).thenReturn(orders);

        assertEquals(5, orderService.getPendingOrders().size());
    }

    @Test
    void placeOrder() {
        var order = generator.nextObject(OrderDTO.class);
        var orderArgCaptor = ArgumentCaptor.forClass(OrderMessage.class);

        orderService.placeOrder(order);

        verify(rabbitTemplate).convertAndSend(any(), any(), orderArgCaptor.capture());
        assertEquals(order.getItems(), orderArgCaptor.getValue().getItems());
    }

    @Test
    void updateOrderStatus_success() {

        var orderSpy = spy(Order.class);
        when(orderRepository.findByCode(any())).thenReturn(Optional.of(orderSpy));
        orderService.updateOrderStatus("TEST1", OrderStatus.IN_PROGRESS);

        verify(orderSpy).setStatus(OrderStatus.IN_PROGRESS);
    }

    @Test
    void updateOrderStatus_throws() {

        when(orderRepository.findByCode(any())).thenReturn(Optional.empty());

        assertThrows(OrderNotFoundException.class, () -> orderService.updateOrderStatus("TEST1", OrderStatus.IN_PROGRESS));

    }

    @Test
    void getOrderStatus_success() {

        var order = generator.nextObject(Order.class);
        when(orderRepository.findByCode(any())).thenReturn(Optional.of(order));

        assertEquals(order.getStatus(), orderService.getOrderStatus(order.getCode()));
    }

    @Test
    void getOrderStatus_throws() {

        when(orderRepository.findByCode(any())).thenReturn(Optional.empty());

        assertThrows(OrderNotFoundException.class, () -> orderService.getOrderStatus("TEST"));
    }
}