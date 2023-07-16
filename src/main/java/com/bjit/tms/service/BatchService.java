package com.bjit.tms.service;

import com.bjit.tms.entity.BatchEntity;
import com.bjit.tms.model.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface BatchService {
    ResponseEntity<Object> batchCreate(BatchCreateModel batchCreateModel);
    List<BatchEntity> allBatches();
    ResponseEntity<Object> assignTrainee(Integer batchId, List<Integer> traineeIds);
    //ResponseEntity<Object> assignTrainer(Integer batchId, List<Integer> trainerIds);
    ResponseEntity<Object> createClassroom(Integer batchId);
    ResponseEntity<Object> getBatchInformation(Integer batchId);
    ResponseEntity<Object> createNotice(Integer trainerId, NoticeModel noticeModel);
    ResponseEntity<List<NoticeListModel>> noticeList(Integer batchId);

}
