package com.bjit.tms.service.implementation;

import com.bjit.tms.entity.assignment_entities.AssignmentCreateEntity;
import com.bjit.tms.entity.assignment_entities.AssignmentSubmitEntity;
import com.bjit.tms.entity.batch_entities.BatchEntity;
import com.bjit.tms.entity.batch_entities.CourseEntity;
import com.bjit.tms.entity.user_entities.TraineeEntity;
import com.bjit.tms.entity.user_entities.TrainerEntity;
import com.bjit.tms.model.assignment_models.AllSubmissions;
import com.bjit.tms.model.assignment_models.AssignmentCreateModel;
import com.bjit.tms.model.assignment_models.AssignmentList;
import com.bjit.tms.model.assignment_models.AssignmentSubmitModel;
import com.bjit.tms.repository.assignment_repositories.AssignmentCreateRepository;
import com.bjit.tms.repository.assignment_repositories.AssignmentSubmitRepository;
import com.bjit.tms.repository.batch_repositories.BatchRepository;
import com.bjit.tms.repository.batch_repositories.CourseRepository;
import com.bjit.tms.repository.user_repositories.TraineeRepository;
import com.bjit.tms.repository.user_repositories.TrainerRepository;
import com.bjit.tms.service.AssignmentService;
import com.bjit.tms.utils.EntityCheck;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
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
    private final EntityCheck entityCheck;
    private final String folder_path = "E:\\Projects\\Final\\Files";
    @Override
    public ResponseEntity<Object> createAssignment(Integer creatorId, AssignmentCreateModel assignmentCreateModel) {

        if (entityCheck.checker("trainer", creatorId)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        TrainerEntity trainerEntity = trainerRepository.findById(creatorId).get();
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
    public ResponseEntity<List<AssignmentList>> assignments(Integer batchId) {

        if (entityCheck.checker("batch", batchId)) {
            return ResponseEntity.notFound().build();
        }

        BatchEntity batch = batchRepository.findById(batchId).get();
        List<AssignmentCreateEntity> assignments = assignmentCreateRepository.findByBatchEntity(batch);
        List<AssignmentList> lists = new ArrayList<AssignmentList>();

        for(AssignmentCreateEntity i : assignments){
            CourseEntity courseEntity = i.getCourseEntity();
            AssignmentList assignmentList = AssignmentList.builder()
                    .assignmentCreator(i.getAssignmentCreator())
                    .courseName(courseEntity.getCourseName())
                    .createdDate(i.getCreatedDate())
                    .deadline(i.getDeadline())
                    .message(i.getMessage())
                    .file(i.getFile())
                    .build();
            lists.add(assignmentList);
        }

        return ResponseEntity.ok(lists);
    }

    @Override
    public ResponseEntity<List<AllSubmissions>> submissions(Integer assignmentId) {


        Optional <AssignmentCreateEntity> optionalAssignment = assignmentCreateRepository.findById(assignmentId);
        if(optionalAssignment.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        AssignmentCreateEntity assignment = optionalAssignment.get();
        List<AssignmentSubmitEntity> lists = assignmentSubmitRepository.findByAssignmentCreateEntity_AssignmentId(assignmentId);
        List<AllSubmissions> submissions = new ArrayList<AllSubmissions>();
        for(AssignmentSubmitEntity i : lists){
            AllSubmissions allSubmissions = AllSubmissions.builder()
                    .assignmentId(assignmentId)
                    .fileName(i.getFile())
                    .submissionDate(i.getSubmissionDate())
                    .traineeName(i.getTraineeName())
                    .build();
            submissions.add(allSubmissions);
        }
        return new ResponseEntity<>(submissions, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> assignmentCreateFile(MultipartFile file, Integer assignmentCreateId) {

//        if (entityCheck.checker("assignmentCreate", assignmentCreateId)){
//            return ResponseEntity.notFound().build();
//        }
        Optional<AssignmentCreateEntity> optionalAssignmentCreateEntity = assignmentCreateRepository.findById(assignmentCreateId);
        if (optionalAssignmentCreateEntity.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        //String filePath = folder_path+"\\Assignments\\"+file.getOriginalFilename();
        String filePath = "D:\\Final Project\\tms\\back-end\\src\\main\\resources\\static\\AssignmentCreateFile\\"+file.getOriginalFilename();
        AssignmentCreateEntity assignmentCreateEntity = optionalAssignmentCreateEntity.get();
        assignmentCreateEntity.setFile(file.getOriginalFilename());
        assignmentCreateEntity.setFilePath(filePath);
        try{
            file.transferTo(new File(filePath));
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body("Couldn't save file");
        }
        assignmentCreateRepository.save(assignmentCreateEntity);

        return new ResponseEntity<>("File Uploaded", HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<?> assignmentSubmitFile(MultipartFile file, Integer assignmentSubmitId) {
//        if(entityCheck.checker("assignmentsubmit", assignmentSubmitId)){
//            return ResponseEntity.notFound().build();
//        }
        Optional<AssignmentSubmitEntity> optionalAssignmentSubmitEntity = assignmentSubmitRepository.findById(assignmentSubmitId);
        if(optionalAssignmentSubmitEntity.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        AssignmentSubmitEntity assignmentSubmitEntity = assignmentSubmitRepository.findById(assignmentSubmitId).get();
        //String filePath = folder_path+"\\Submissions\\"+file.getOriginalFilename();
        String filePath ="D:\\Final Project\\tms\\back-end\\src\\main\\resources\\static\\AssignmentSubmitFile\\"+file.getOriginalFilename();
        assignmentSubmitEntity.setFile(file.getOriginalFilename());
        assignmentSubmitEntity.setFileType(file.getContentType());
        assignmentSubmitEntity.setPathname(filePath);
        try{
            file.transferTo(new File(filePath));
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body("Couldn't save file");
        }
        return new ResponseEntity<>("Assignment Uploaded", HttpStatus.OK);
    }


}
