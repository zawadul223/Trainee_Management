package com.bjit.tms.repository;

import com.bjit.tms.entity.BatchEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BatchRepository extends JpaRepository<BatchEntity, Integer> {

}
