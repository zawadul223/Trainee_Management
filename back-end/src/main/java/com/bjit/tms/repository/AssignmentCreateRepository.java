package com.bjit.tms.repository;

import com.bjit.tms.entity.AssignmentCreateEntity;
import com.bjit.tms.entity.BatchEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssignmentCreateRepository extends JpaRepository<AssignmentCreateEntity, Integer> {
    List<AssignmentCreateEntity> findByBatchEntity(BatchEntity batchEntity);
}
