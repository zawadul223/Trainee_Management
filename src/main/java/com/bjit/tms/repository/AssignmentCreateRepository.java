package com.bjit.tms.repository;

import com.bjit.tms.entity.AssignmentCreateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssignmentCreateRepository extends JpaRepository<AssignmentCreateEntity, Integer> {
}
