package com.bjit.tms.repository;

import com.bjit.tms.entity.TrainerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainerRepository extends JpaRepository<TrainerEntity, Integer> {
}
