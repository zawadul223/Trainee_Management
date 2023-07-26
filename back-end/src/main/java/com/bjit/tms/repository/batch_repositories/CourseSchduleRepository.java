package com.bjit.tms.repository.batch_repositories;

import com.bjit.tms.entity.batch_entities.CourseEntity;
import com.bjit.tms.entity.batch_entities.CourseScheduleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseSchduleRepository extends JpaRepository<CourseScheduleEntity,Integer> {

    @Query("SELECT cs.scheduleId FROM CourseScheduleEntity cs WHERE cs.trainerId = :trainerId")
    Integer findScheduleIdByTrainerId(@Param("trainerId") Integer trainerId);

    CourseScheduleEntity findByCourseEntity_CourseId(Integer courseId);
    List<CourseScheduleEntity> findByCourseEntity(CourseEntity courseEntity);

}
