package com.san.adesso.pizza_order_manager.controller;

import com.san.adesso.pizza_order_manager.entity.OrderDTO;
import com.san.adesso.pizza_order_manager.entity.OrderStatus;
import com.san.adesso.pizza_order_manager.service.OrderService;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderControllerTest {

    @Mock
    private OrderService orderService;

    @InjectMocks
    private OrderController orderController;

    EasyRandom generator = new EasyRandom();

    @Test
    void testGetPendingOrders_returnsListOfOrders() {

        var mockOrders = generator.objects(OrderDTO.class, 5);
        when(orderService.getPendingOrders()).thenReturn(mockOrders.toList());

        var response = orderController.getPendingOrders();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(5, response.getBody().size());
    }

    @Test
    void testPlaceOrder_returnsCreatedStatusOnSuccess() {
        var orderDTO = new OrderDTO();
        doNothing().when(orderService).placeOrder(orderDTO);

        var response = orderController.placeOrder(orderDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Order placed successfully", response.getBody());
    }

    @Test
    void testPlaceOrder_returnsInternalServerErrorOnFailure() {
        var orderDTO = new OrderDTO();
        doThrow(new RuntimeException("Database error")).when(orderService).placeOrder(orderDTO);

        var response = orderController.placeOrder(orderDTO);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Failed to place order", response.getBody());
    }

    @Test
    void testUpdateOrderStatus_returnsOkStatusOnSuccess() {
        var orderCode = "12345";
        OrderStatus newStatus = OrderStatus.COMPLETED;
        doNothing().when(orderService).updateOrderStatus(orderCode, newStatus);

        var response = orderController.updateOrderStatus(orderCode, newStatus);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Order status updated to " + newStatus, response.getBody());
    }

    @Test
    void testUpdateOrderStatus_returnsInternalServerErrorOnFailure() {
        var orderCode = "12345";
        var newStatus = OrderStatus.COMPLETED;
        doThrow(new RuntimeException("Update error")).when(orderService).updateOrderStatus(orderCode, newStatus);

        var response = orderController.updateOrderStatus(orderCode, newStatus);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Failed to update order status", response.getBody());
    }

    @Test
    void testGetOrderStatus_returnsOrderStatus() {
        var orderCode = "12345";
        var status = OrderStatus.PENDING;
        when(orderService.getOrderStatus(orderCode)).thenReturn(status);

        var response = orderController.getOrderStatus(orderCode);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(status, response.getBody());
    }
}