package com.bjit.tms.repository;

import com.bjit.tms.entity.ClassroomEntity;
import com.bjit.tms.entity.ClassroomPostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<ClassroomPostEntity, Integer> {

    List<ClassroomPostEntity> findByClassroomEntity(ClassroomEntity classroomEntity);
}
