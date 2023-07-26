package com.bjit.tms.repository.assignment_repositories;

import com.bjit.tms.entity.assignment_entities.AssignmentSubmitEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssignmentSubmitRepository extends JpaRepository<AssignmentSubmitEntity, Integer> {
    List<AssignmentSubmitEntity> findByAssignmentCreateEntity_AssignmentId(Integer assignmentId);
}
