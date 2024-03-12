package com.study.grpc.account.service.dao;

import com.study.grpc.account.service.model.DbPet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PetRepository extends JpaRepository<DbPet, Long> {

    List<DbPet> findAllByOwnerId(Long ownerId);
}
