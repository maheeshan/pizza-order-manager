package com.san.adesso.pizza_order_manager.mapper;

import com.san.adesso.pizza_order_manager.entity.Order;
import com.san.adesso.pizza_order_manager.entity.OrderDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface OrderMapper {

    @Mapping(target = "id", ignore = true)
    OrderDTO toOrderDTO(Order order);

    Order toOrder(OrderDTO orderDTO);
}
