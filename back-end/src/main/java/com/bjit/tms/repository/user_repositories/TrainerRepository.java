package com.bjit.tms.repository.user_repositories;

import com.bjit.tms.entity.user_entities.TrainerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainerRepository extends JpaRepository<TrainerEntity, Integer> {
    TrainerEntity findByTrainerEmail(String email);
}
