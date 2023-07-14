package com.bjit.tms.repository;

import com.bjit.tms.entity.CourseScheduleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseSchduleRepository extends JpaRepository<CourseScheduleEntity,Integer> {

    @Query("SELECT cs.scheduleId FROM CourseScheduleEntity cs WHERE cs.trainerId = :trainerId")
    Integer findScheduleIdByTrainerId(@Param("trainerId") Integer trainerId);

}
