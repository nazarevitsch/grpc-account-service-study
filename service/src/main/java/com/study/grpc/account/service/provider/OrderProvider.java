package com.study.grpc.account.service.provider;

import com.study.grpc.account.client.Order;
import com.study.grpc.account.client.OrderServiceGrpc;
import com.study.grpc.account.client.Orders;
import com.study.grpc.account.service.service.OrderProducer;
import com.study.grpc.account.service.service.OrderService;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;

@Slf4j
@GrpcService
@RequiredArgsConstructor
public class OrderProvider extends OrderServiceGrpc.OrderServiceImplBase {

    private final static int MILLISECONDS_TO_WAIT = 1234;
    private final OrderService orderService;

    @Override
    public StreamObserver<Order> create(StreamObserver<Orders> responseObserver) {
        return new StreamObserver<>() {
            final Orders.Builder ordersBuilder = Orders.newBuilder();

            @Override
            public void onNext(Order order) {
                Order savedOrder = orderService.create(order);
                ordersBuilder.addOrders(savedOrder);
                if (savedOrder.getId() == 8300L) {
                    log.info("Reached SIZE !!!");
                    onCompleted();
                }
            }

            @Override
            public void onError(Throwable throwable) {
                responseObserver.onError(throwable);
            }

            @Override
            public void onCompleted() {
                responseObserver.onNext(ordersBuilder.build());
                responseObserver.onCompleted();
                log.info("Completed !!!");
            }
        };
    }

    @Override
    public StreamObserver<Order> getWithFilter(StreamObserver<Order> responseObserver) {
        return new StreamObserver<>() {

            private OrderProducer producer;

            @Override
            public void onNext(Order order) {
                log.info("Get orders with brand: {}, state: {}", order.getBrand(), order.getState());
                try {
                    producer.stop();
                } catch (Exception e){}
                producer = new OrderProducer(order, orderService, responseObserver);
                producer.start();
            }

            @Override
            public void onError(Throwable throwable) {
                responseObserver.onError(throwable);
            }

            @Override
            public void onCompleted() {
                try {
                    producer.join();
                } catch (InterruptedException e){}
                responseObserver.onCompleted();
                log.info("Completed !!!");
            }
        };
    }

    private static void sleep() {
        try {
            Thread.sleep(MILLISECONDS_TO_WAIT);
        } catch (Exception e) {}
    }
}
