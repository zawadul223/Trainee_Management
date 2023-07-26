package com.bjit.tms.repository.classroom_repositories;

import com.bjit.tms.entity.classroom_entities.ClassroomEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassroomRepository extends JpaRepository<ClassroomEntity, Integer> {
}
