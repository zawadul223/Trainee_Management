package com.bjit.tms.repository.classroom_repositories;

import com.bjit.tms.entity.classroom_entities.ClassroomEntity;
import com.bjit.tms.entity.classroom_entities.ClassroomPostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<ClassroomPostEntity, Integer> {

    List<ClassroomPostEntity> findByClassroomEntity(ClassroomEntity classroomEntity);
}
