package com.study.grpc.account.service.service;

import com.study.grpc.account.client.Order;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class OrderProducer extends Thread {

    private final static int MILLISECONDS_TO_WAIT = 1234;
    private final Order filters;
    private final OrderService orderService;
    private final StreamObserver<Order> responseObserver;

    public OrderProducer(Order filters,
                         OrderService orderService,
                         StreamObserver<Order> responseObserver) {
        this.filters = filters;
        this.orderService = orderService;
        this.responseObserver = responseObserver;
    }

    @Override
    public synchronized void run() {
        log.info("Searching with brand: {}, state: {}", filters.getBrand(), filters.getState());
        List<Order> orders = orderService.getByFilters(filters.getBrand(), filters.getState());
        log.info("Orders size: {}", orders.size());
        orders.forEach(orderToSend -> {
            responseObserver.onNext(orderToSend);
            log.info("Order with id: {}, was sent.", orderToSend.getId());
            sleep();
        });
        log.info("Finished with orders with brand: {}, state: {}", filters.getBrand(), filters.getState());
    }

    private static void sleep() {
        try {
            sleep(MILLISECONDS_TO_WAIT);
        } catch (InterruptedException e) {}
    }
}
