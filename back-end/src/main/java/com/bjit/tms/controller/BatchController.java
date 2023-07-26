package com.bjit.tms.controller;

import com.bjit.tms.entity.batch_entities.BatchEntity;
import com.bjit.tms.model.batch_models.BatchCreateModel;
import com.bjit.tms.model.batch_models.NoticeListModel;
import com.bjit.tms.model.batch_models.NoticeModel;
import com.bjit.tms.repository.batch_repositories.BatchRepository;
import com.bjit.tms.service.BatchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/batch")
@RequiredArgsConstructor
public class BatchController {


    private final BatchService batchService;
    private final BatchRepository batchRepository;
    @PostMapping("/create")
    public ResponseEntity<Object> create(@RequestBody BatchCreateModel batchCreateModel){
        return batchService.batchCreate(batchCreateModel);
    }

    @PostMapping("/classroom/create/{batchId}")
    public ResponseEntity<Object> createClassroom(@PathVariable Integer batchId){
        return batchService.createClassroom(batchId);
    }

    @GetMapping("/all")
    public List<BatchEntity> all(){
        return batchService.allBatches();
    }

    @PostMapping("/assign/trainee/{batchId}")
    public ResponseEntity<Object> assignTrainee(@PathVariable Integer batchId, @RequestBody List<Integer> trainees){
        return batchService.assignTrainee(batchId, trainees);
    }

    @PostMapping("/assign/trainer/{batchId}")
    public ResponseEntity<Object> assignTrainer(@PathVariable Integer batchId, @RequestBody List<Integer> trainers){
        return batchService.assignTrainer(batchId, trainers);
    }

    @GetMapping("/details/{batchId}")
    public ResponseEntity<Object> detailModel(@PathVariable Integer batchId){
        return batchService.getBatchInformation(batchId);
    }

    @PostMapping("/notice/create/{trainerId}")
    public ResponseEntity<Object> giveNotice(@PathVariable Integer trainerId, @RequestBody NoticeModel noticeModel){
        return batchService.createNotice(trainerId, noticeModel);
    }

    @GetMapping("/notice/getNotices/{batchId}")
    public ResponseEntity<List<NoticeListModel>> getNotices(@PathVariable Integer batchId){
        return batchService.noticeList(batchId);
    }

    @GetMapping("/name/{batchId}")
    public ResponseEntity<?> getBatchName(@PathVariable Integer batchId){
        return batchService.batchName(batchId);
    }

}
