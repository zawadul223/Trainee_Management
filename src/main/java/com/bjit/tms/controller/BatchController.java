package com.bjit.tms.controller;

import com.bjit.tms.entity.BatchEntity;
import com.bjit.tms.model.BatchCreateModel;
import com.bjit.tms.model.BatchDetailModel;
import com.bjit.tms.model.CourseCreateModel;
import com.bjit.tms.service.BatchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/batch")
@RequiredArgsConstructor
public class BatchController {


    private final BatchService batchService;
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

//    @PostMapping("/assign/trainer/{batchId}")
//    public ResponseEntity<Object> assignTrainer(@PathVariable Integer batchId, @RequestBody List<Integer> trainers){
//        return batchService.assignTrainer(batchId, trainers);
//    }

    @GetMapping("/details/{batchId}")
    public ResponseEntity<Object> detailModel(@PathVariable Integer batchId){
        return batchService.getBatchInformation(batchId);
    }


}
