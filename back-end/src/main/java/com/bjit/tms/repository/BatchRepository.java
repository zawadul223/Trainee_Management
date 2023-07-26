package com.bjit.tms.repository;

import com.bjit.tms.entity.BatchEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BatchRepository extends JpaRepository<BatchEntity, Integer> {


    @Query("SELECT b FROM BatchEntity b JOIN b.traineeEntityList t WHERE t.traineeId = :traineeId")
    BatchEntity findBatchByTraineeId(@Param("traineeId") Integer traineeId);

    @Query("SELECT b.batchId FROM BatchEntity b JOIN b.trainerEntityList t WHERE t.trainerId = :trainerId")
    List<Integer> findBatchIdsByTrainerId(@Param("trainerId") Integer trainerId);



}
