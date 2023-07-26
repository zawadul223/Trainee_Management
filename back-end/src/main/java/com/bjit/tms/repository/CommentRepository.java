package com.bjit.tms.repository;

import com.bjit.tms.entity.ClassroomPostEntity;
import com.bjit.tms.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<CommentEntity, Integer> {
    List<CommentEntity> findCommentEntitiesByClassroomPostEntity(ClassroomPostEntity classroomPostEntity);
}
