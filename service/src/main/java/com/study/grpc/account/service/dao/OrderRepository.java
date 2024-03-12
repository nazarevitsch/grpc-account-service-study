package com.study.grpc.account.service.dao;

import com.study.grpc.account.service.model.DbOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<DbOrder, Long> {

    List<DbOrder> findAllByBrandAndState(String brand, String state);
}
