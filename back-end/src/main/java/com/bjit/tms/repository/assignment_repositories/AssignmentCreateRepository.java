package com.bjit.tms.repository.assignment_repositories;

import com.bjit.tms.entity.assignment_entities.AssignmentCreateEntity;
import com.bjit.tms.entity.batch_entities.BatchEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssignmentCreateRepository extends JpaRepository<AssignmentCreateEntity, Integer> {
    List<AssignmentCreateEntity> findByBatchEntity(BatchEntity batchEntity);
}
