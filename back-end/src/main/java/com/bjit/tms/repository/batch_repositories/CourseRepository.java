package com.bjit.tms.repository.batch_repositories;

import com.bjit.tms.entity.batch_entities.CourseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<CourseEntity, Integer> {
}
