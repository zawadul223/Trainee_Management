package com.bjit.tms.repository;

import com.bjit.tms.entity.ClassroomEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassroomRepository extends JpaRepository<ClassroomEntity, Integer> {
}
