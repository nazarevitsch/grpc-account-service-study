package com.study.grpc.account.service.service;

import com.study.grpc.account.client.Order;
import com.study.grpc.account.service.converter.OrderConverter;
import com.study.grpc.account.service.dao.OrderRepository;
import com.study.grpc.account.service.model.DbOrder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderConverter converter;
    private final OrderRepository repository;

    public Order create(Order dto) {
        DbOrder order = this.converter.fromDtoToDb(dto);
        DbOrder savedOrder = this.repository.save(order);
        log.info("Order with id: {}, was saved", savedOrder.getId());
        return this.converter.fromDbToDto(savedOrder);
    }

    public List<Order> getByFilters (String brand, String state) {
        return this.repository.findAllByBrandAndState(brand, state)
                .stream()
                .map(this.converter::fromDbToDto)
                .toList();
    }
}
