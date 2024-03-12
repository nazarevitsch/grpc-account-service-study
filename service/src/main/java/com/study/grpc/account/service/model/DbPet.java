package com.study.grpc.account.service.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "pet")
public class DbPet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Long ownerId;
}
