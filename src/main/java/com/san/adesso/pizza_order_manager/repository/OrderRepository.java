package com.san.adesso.pizza_order_manager.repository;

import com.san.adesso.pizza_order_manager.entity.Order;
import com.san.adesso.pizza_order_manager.entity.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByStatus(OrderStatus status);
}
