package com.bjit.tms.service.implementation;

import com.bjit.tms.entity.*;
import com.bjit.tms.model.BatchCreateModel;
import com.bjit.tms.model.CourseCreateModel;
import com.bjit.tms.repository.*;
import com.bjit.tms.service.BatchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BatchServiceImpl implements BatchService {

    private final BatchRepository batchRepository;
    private final TraineeRepository traineeRepository;
    private final TrainerRepository trainerRepository;
    private final ClassroomRepository classroomRepository;
    private final CourseRepository courseRepository;

    @Override
    public ResponseEntity<Object> batchCreate(BatchCreateModel batchCreateModel) {
        BatchEntity batchEntity = BatchEntity.builder()
                .batchName(batchCreateModel.getBatchName())
                .startDate(batchCreateModel.getStartDate())
                .endDate(batchCreateModel.getEndDate())
                .build();
        BatchEntity savedBatch = batchRepository.save(batchEntity);
        return new ResponseEntity<>(savedBatch, HttpStatus.CREATED);
    }

    @Override
    public List<BatchEntity> allBatches() {
        return batchRepository.findAll();
    }

    @Override
    public ResponseEntity<Object> assignTrainee(Integer batchId, List<Integer> traineeIds) {
        Optional<BatchEntity> optionalBatch = batchRepository.findById(batchId);
        if (optionalBatch.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        BatchEntity batchEntity = optionalBatch.get();

        List<TraineeEntity> trainees = traineeRepository.findAllById(traineeIds);
        // batchEntity.setTraineeEntityList(trainees); // Assuming there's a setter for the trainees property in the Batch entity
        batchEntity.getTraineeEntityList().addAll(trainees);
        batchRepository.save(batchEntity);

        return new ResponseEntity<>("Assigned Successfully", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> assignTrainer(Integer batchId, List<Integer> trainerIds) {
        Optional<BatchEntity> optionalBatch = batchRepository.findById(batchId);
        if (optionalBatch.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        BatchEntity batchEntity = optionalBatch.get();

        List<TrainerEntity> trainers = trainerRepository.findAllById(trainerIds);
        batchEntity.setTrainerEntityList(trainers); // Assuming there's a setter for the trainees property in the Batch entity

        batchRepository.save(batchEntity);

        return new ResponseEntity<>("Assigned Successfully", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> createClassroom(Integer batchId) {
        Optional<BatchEntity> optionalBatch = batchRepository.findById(batchId);
        if (optionalBatch.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        BatchEntity batchEntity = optionalBatch.get();

        ClassroomEntity classroomEntity = ClassroomEntity.builder()
                .batchEntity(batchEntity)
                .build();
        classroomRepository.save(classroomEntity);

        batchEntity.setClassroomEntity(classroomEntity);

        return new ResponseEntity<>("Assigned Successfully", HttpStatus.OK);
    }


}