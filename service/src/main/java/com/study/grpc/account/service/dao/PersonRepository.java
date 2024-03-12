package com.study.grpc.account.service.dao;

import com.study.grpc.account.service.model.DbPerson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonRepository extends JpaRepository<DbPerson, Long> {

    List<DbPerson> findAllByIdNotIn(List<Long> ids);
}
