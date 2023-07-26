package com.bjit.tms.service;

import com.bjit.tms.entity.batch_entities.BatchEntity;
import com.bjit.tms.model.batch_models.BatchCreateModel;
import com.bjit.tms.model.batch_models.NoticeListModel;
import com.bjit.tms.model.batch_models.NoticeModel;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BatchService {
    ResponseEntity<Object> batchCreate(BatchCreateModel batchCreateModel);
    List<BatchEntity> allBatches();
    ResponseEntity<Object> assignTrainee(Integer batchId, List<Integer> traineeNames);
    ResponseEntity<Object> assignTrainer(Integer batchId, List<Integer> trainerIds);
    ResponseEntity<Object> createClassroom(Integer batchId);
    ResponseEntity<Object> getBatchInformation(Integer batchId);
    ResponseEntity<Object> createNotice(Integer trainerId, NoticeModel noticeModel);
    ResponseEntity<List<NoticeListModel>> noticeList(Integer batchId);
    ResponseEntity<?> batchName(Integer batchId);
}
