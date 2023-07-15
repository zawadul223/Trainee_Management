package com.bjit.tms.service.implementation;

import com.bjit.tms.entity.*;
import com.bjit.tms.model.AllSubmissions;
import com.bjit.tms.model.AssignmentCreateModel;
import com.bjit.tms.model.AssignmentList;
import com.bjit.tms.model.AssignmentSubmitModel;
import com.bjit.tms.repository.*;
import com.bjit.tms.service.AssignmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AssignmentServiceImpl implements AssignmentService {
    private final AssignmentCreateRepository assignmentCreateRepository;
    private final CourseRepository courseRepository;
    private final TrainerRepository trainerRepository;
    private final TraineeRepository traineeRepository;
    private final AssignmentSubmitRepository assignmentSubmitRepository;
    private final BatchRepository batchRepository;
    @Override
    public ResponseEntity<Object> createAssignment(Integer creatorId, AssignmentCreateModel assignmentCreateModel) {

        Optional<TrainerEntity> optionalTrainer = trainerRepository.findById(creatorId);
        if (optionalTrainer.isEmpty()) {
            return new ResponseEntity<>("Trainer not found", HttpStatus.NOT_FOUND);
        }
        TrainerEntity trainerEntity = optionalTrainer.get();
        String assignmentCreator = trainerEntity.getName();

        Integer courseId = assignmentCreateModel.getCourseId();
        Optional<CourseEntity> optionalCourse = courseRepository.findById(courseId);
        if (optionalCourse.isEmpty()) {
            return new ResponseEntity<>("Course not found", HttpStatus.NOT_FOUND);
        }

        CourseEntity courseEntity = optionalCourse.get();

        Integer batchId = assignmentCreateModel.getBatchId();
        Optional<BatchEntity> optionalBatch = batchRepository.findById(batchId);
        if (optionalBatch.isEmpty()) {
            return new ResponseEntity<>("Course not found", HttpStatus.NOT_FOUND);
        }

        BatchEntity batchEntity = optionalBatch.get();

        AssignmentCreateEntity assignmentCreateEntity = AssignmentCreateEntity.builder()
                .file(assignmentCreateModel.getFile())
                .createdDate(Date.valueOf(LocalDate.now()))
                .deadline(assignmentCreateModel.getDeadline())
                .message(assignmentCreateModel.getMessage())
                .assignmentCreator(assignmentCreator)
                .build();

        assignmentCreateEntity.setCourseEntity(courseEntity);
        assignmentCreateEntity.setBatchEntity(batchEntity);
        assignmentCreateRepository.save(assignmentCreateEntity);

//        List<AssignmentCreateEntity> assignmentList = new ArrayList<AssignmentCreateEntity>();
//        assignmentList.add(assignmentCreateEntity);
//
//        courseEntity.setAssignmentCreateEntities(assignmentList);
        return new ResponseEntity<>("Assignment created",HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Object> submitAssignment(Integer traineeId, AssignmentSubmitModel assignmentSubmitModel) {
        Optional<TraineeEntity> optionalTrainee = traineeRepository.findById(traineeId);
        if (optionalTrainee.isEmpty()) {
            return new ResponseEntity<>("Trainee not found", HttpStatus.NOT_FOUND);
        }
        TraineeEntity traineeEntity = optionalTrainee.get();

        Integer assignmentId = assignmentSubmitModel.getAssignmentId();
        Optional<AssignmentCreateEntity> optionalAssignment = assignmentCreateRepository.findById(assignmentId);
        if (optionalAssignment.isEmpty()) {
            return new ResponseEntity<>("Assignment not found", HttpStatus.NOT_FOUND);
        }
        AssignmentCreateEntity assignmentEntity = optionalAssignment.get();

        AssignmentSubmitEntity assignmentSubmitEntity = AssignmentSubmitEntity.builder()
                .submissionDate(Date.valueOf(LocalDate.now()))
                .file(assignmentSubmitModel.getFile())
                .traineeName(traineeEntity.getName())
                .build();
        assignmentSubmitEntity.setAssignmentCreateEntity(assignmentEntity);
        //assignmentSubmitEntity.setTraineeEntity(traineeEntity);
        assignmentSubmitRepository.save(assignmentSubmitEntity);
        return new ResponseEntity<>("Assignment Submitted", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<AssignmentCreateEntity>> assignments(Integer batchId) {
        Optional<BatchEntity> optionalBatch = batchRepository.findById(batchId);
        if (optionalBatch.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        BatchEntity batch = optionalBatch.get();
        List<AssignmentCreateEntity> assignments = assignmentCreateRepository.findByBatchEntity(batch);

        return ResponseEntity.ok(assignments);
    }

    @Override
    public List<AllSubmissions> submissions(Integer assignmentId) {

        List<AssignmentSubmitEntity> lists = assignmentSubmitRepository.findByAssignmentCreateEntity_AssignmentId(assignmentId);
        AssignmentCreateEntity assignment = assignmentCreateRepository.findById(assignmentId).get();

        return null;
    }

}
