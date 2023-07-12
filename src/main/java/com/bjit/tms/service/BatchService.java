package com.bjit.tms.service;

import com.bjit.tms.entity.BatchEntity;
import com.bjit.tms.model.BatchCreateModel;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BatchService {
    ResponseEntity<Object> batchCreate(BatchCreateModel batchCreateModel);
    List<BatchEntity> allBatches();
    ResponseEntity<Object> assignTrainee(Integer batchId, List<Integer> traineeIds);
    ResponseEntity<Object> assignTrainer(Integer batchId, List<Integer> trainerIds);
}
