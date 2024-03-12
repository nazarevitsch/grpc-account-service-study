package com.study.grpc.account.service.converter;

import com.study.grpc.account.client.Order;
import com.study.grpc.account.service.model.DbOrder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class OrderConverter {

    public Order fromDbToDto(DbOrder order) {
        Order.Builder builder = Order.newBuilder();
        Optional.ofNullable(order.getId()).ifPresent(builder::setId);
        Optional.ofNullable(order.getBrand()).ifPresent(builder::setBrand);
        Optional.ofNullable(order.getState()).ifPresent(builder::setState);
        Optional.ofNullable(order.getPrice()).ifPresent(builder::setPrice);
        return builder.build();
    }

    public DbOrder fromDtoToDb(Order dto) {
        DbOrder order = new DbOrder();
        if (dto.hasId()) order.setId(dto.getId());
        order.setBrand(dto.getBrand());
        order.setState(dto.getState());
        order.setPrice(dto.getPrice());
        return order;
    }
}
