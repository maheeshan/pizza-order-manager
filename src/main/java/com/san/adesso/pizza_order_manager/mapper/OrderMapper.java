package com.san.adesso.pizza_order_manager.mapper;

import com.san.adesso.pizza_order_manager.entity.Order;
import com.san.adesso.pizza_order_manager.entity.OrderDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    OrderDTO toOrderDTO(Order order);

    @Mapping(target = "id", ignore = true)
    Order toOrder(OrderDTO orderDTO);
}
