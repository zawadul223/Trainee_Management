package com.bjit.tms.repository;

import com.bjit.tms.entity.TraineeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TraineeRepository extends JpaRepository<TraineeEntity, Integer> {
    TraineeEntity findByTraineeEmail(String email);
    TraineeEntity findByName(String name);
    //List<TraineeEntity> findAll();
}
