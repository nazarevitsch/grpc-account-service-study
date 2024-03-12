package com.study.grpc.account.service.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "person_order")
public class DbOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String brand;

    private String state;

    private Integer price;
}
