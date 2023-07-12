package com.bjit.tms.controller;

import com.bjit.tms.entity.BatchEntity;
import com.bjit.tms.model.BatchCreateModel;
import com.bjit.tms.service.BatchService;
import com.bjit.tms.service.implementation.BatchServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/batch")
@RequiredArgsConstructor
public class BatchController {


    private final BatchService batchService;
    @PostMapping("/create")
    public ResponseEntity<Object> create(@RequestBody BatchCreateModel batchCreateModel){
        return batchService.batchCreate(batchCreateModel);
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
        return batchService.assignTrainee(batchId, trainers);
    }
}
