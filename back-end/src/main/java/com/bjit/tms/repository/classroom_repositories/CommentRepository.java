package com.bjit.tms.repository.classroom_repositories;

import com.bjit.tms.entity.classroom_entities.ClassroomPostEntity;
import com.bjit.tms.entity.classroom_entities.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<CommentEntity, Integer> {
    List<CommentEntity> findCommentEntitiesByClassroomPostEntity(ClassroomPostEntity classroomPostEntity);
}
