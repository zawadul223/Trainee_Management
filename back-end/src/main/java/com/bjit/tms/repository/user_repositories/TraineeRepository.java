package com.bjit.tms.repository.user_repositories;

import com.bjit.tms.entity.user_entities.TraineeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TraineeRepository extends JpaRepository<TraineeEntity, Integer> {
    TraineeEntity findByTraineeEmail(String email);
    TraineeEntity findByName(String name);
    //List<TraineeEntity> findAll();
}
