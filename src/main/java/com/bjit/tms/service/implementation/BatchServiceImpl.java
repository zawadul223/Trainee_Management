package com.bjit.tms.service.implementation;

import com.bjit.tms.entity.*;
import com.bjit.tms.model.batch_models.*;
import com.bjit.tms.repository.*;
import com.bjit.tms.service.BatchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BatchServiceImpl implements BatchService {

    private final BatchRepository batchRepository;
    private final TraineeRepository traineeRepository;
    private final TrainerRepository trainerRepository;
    private final ClassroomRepository classroomRepository;
    private final CourseRepository courseRepository;
    private final CourseSchduleRepository courseSchduleRepository;
    private final NoticeRepository noticeRepository;

    @Override
    public ResponseEntity<Object> batchCreate(BatchCreateModel batchCreateModel) {
        BatchEntity batchEntity = BatchEntity.builder()
                .batchName(batchCreateModel.getBatchName())
                .startDate(batchCreateModel.getStartDate())
                .endDate(batchCreateModel.getEndDate())
                .build();
        BatchEntity savedBatch = batchRepository.save(batchEntity);
        Integer id = savedBatch.getBatchId();
        createClassroom(id);

        return ResponseEntity.ok().body(Map.of("success", true,
                "id", id));
    }

    @Override
    public List<BatchEntity> allBatches() {
        return batchRepository.findAll();
    }

    @Override
    public ResponseEntity<Object> assignTrainee(Integer batchId, List<String> traineeNames) {
        Optional<BatchEntity> optionalBatch = batchRepository.findById(batchId);
        if (optionalBatch.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        BatchEntity batchEntity = optionalBatch.get();
        List<TraineeEntity> trainees = new ArrayList<TraineeEntity>();

        for (String name : traineeNames) {
            TraineeEntity traineeEntity = traineeRepository.findByName(name);
            traineeEntity.setAssignedBatch(true);
            trainees.add(traineeEntity);
        }
        batchEntity.setTraineeEntityList(trainees);
        batchRepository.save(batchEntity);

        return new ResponseEntity<>("Assigned Successfully", HttpStatus.OK);
    }

//    @Override
//    public ResponseEntity<Object> assignTrainer(Integer batchId, List<Integer> trainerIds) {
//        Optional<BatchEntity> optionalBatch = batchRepository.findById(batchId);
//        if (optionalBatch.isEmpty()) {
//            return ResponseEntity.notFound().build();
//        }
//
//        BatchEntity batchEntity = optionalBatch.get();
//
//        List<TrainerEntity> trainers = trainerRepository.findAllById(trainerIds);
//        batchEntity.setTrainerEntityList(trainers); // Assuming there's a setter for the trainees property in the Batch entity
//
//        batchRepository.save(batchEntity);
//
//        return new ResponseEntity<>("Assigned Successfully", HttpStatus.OK);
//    }

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

    @Override
    public ResponseEntity<Object> getBatchInformation(Integer batchId) {
        Optional<BatchEntity> optionalBatch = batchRepository.findById(batchId);
        if (optionalBatch.isEmpty()) {
            // Handle case when batch is not found
            return new ResponseEntity<Object>("Batch not found", HttpStatus.NOT_FOUND);
        }

        BatchEntity batchEntity = optionalBatch.get();

        // Fetch trainee names for the batch
        List<String> traineeNames = batchEntity.getTraineeEntityList()
                .stream()
                .map(TraineeEntity::getName)
                .collect(Collectors.toList());

        // Fetch course details with assigned trainers for the batch
        List<CourseResponseModel> courses = new ArrayList<>();
        for (CourseEntity courseEntity : batchEntity.getCourseEntityList()) {
            CourseResponseModel courseResponse = new CourseResponseModel();
            Integer courseId = courseEntity.getCourseId();
            CourseScheduleEntity courseScheduleEntity = courseSchduleRepository.findByCourseEntity_CourseId(courseId);
            courseResponse.setCourseName(courseEntity.getCourseName());
            courseResponse.setStartDate(courseScheduleEntity.getStartDate());
            courseResponse.setEndDate(courseScheduleEntity.getEndDate());

            // Fetch course schedules for the course
            List<CourseScheduleEntity> courseSchedules = courseSchduleRepository.findByCourseEntity(courseEntity);

            // Fetch assigned trainers for each course schedule
            List<String> trainerNames = courseSchedules.stream()
                    .map(CourseScheduleEntity::getTrainerId)
                    .map(trainerId -> {
                        Optional<TrainerEntity> optionalTrainer = trainerRepository.findById(trainerId);
                        return optionalTrainer.map(TrainerEntity::getName).orElse("");
                    })
                    .collect(Collectors.toList());

            courseResponse.setTrainerNames(trainerNames);
            courses.add(courseResponse);
        }

        List<NoticeEntity> noticeEntity = noticeRepository.findNoticeEntitiesByBatchEntity(batchEntity);
        List<NoticeListModel> noticeListModels = new ArrayList<NoticeListModel>();
        for(NoticeEntity n : noticeEntity){
            NoticeListModel noticeListModel = NoticeListModel.builder()
                    .notice(n.getNotice())
                    .time(n.getTime())
                    .trainerName(n.getTrainerEntity().getName())
                    .build();
            noticeListModels.add(noticeListModel);
        }
        // Create and return the batch response model
        BatchDetailModel batchResponse = new BatchDetailModel();
        batchResponse.setBatchName(batchEntity.getBatchName());
        batchResponse.setStartDate(batchEntity.getStartDate());
        batchResponse.setEndDate(batchEntity.getEndDate());
        batchResponse.setTraineeNames(traineeNames);
        batchResponse.setCourses(courses);
        batchResponse.setNotices(noticeListModels);

        return new ResponseEntity<>(batchResponse, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> createNotice(Integer trainerId, NoticeModel noticeModel) {

        Integer batchId = noticeModel.getBatchId();
        Optional<BatchEntity> optionalBatch = batchRepository.findById(batchId);
        if (optionalBatch.isEmpty()) {
            // Handle case when batch is not found
            return new ResponseEntity<Object>("Batch not found", HttpStatus.NOT_FOUND);
        }
        BatchEntity batchEntity = optionalBatch.get();

        Optional<TrainerEntity> optionalTrainer = trainerRepository.findById(trainerId);
        if (optionalTrainer.isEmpty()) {
            return new ResponseEntity<>("Trainer not found", HttpStatus.NOT_FOUND);
        }
        TrainerEntity trainerEntity = optionalTrainer.get();

        NoticeEntity noticeEntity = NoticeEntity.builder()
                .notice(noticeModel.getNotice())
                .batchEntity(batchEntity)
                .trainerEntity(trainerEntity)
                .time(Date.valueOf(LocalDate.now()))
                .build();

        noticeRepository.save(noticeEntity);
        return new ResponseEntity<>("Notice Created", HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<List<NoticeListModel>> noticeList(Integer batchId) {
        Optional<BatchEntity> optionalBatch = batchRepository.findById(batchId);
        if (optionalBatch.isEmpty()) {
            // Handle case when batch is not found
            return ResponseEntity.notFound().build();
        }
        BatchEntity batchEntity = optionalBatch.get();

        List<NoticeEntity> noticeEntity = noticeRepository.findNoticeEntitiesByBatchEntity(batchEntity);
        List<NoticeListModel> noticeListModels = new ArrayList<NoticeListModel>();
        for(NoticeEntity n : noticeEntity){
            NoticeListModel noticeListModel = NoticeListModel.builder()
                    .notice(n.getNotice())
                    .time(n.getTime())
                    .trainerName(n.getTrainerEntity().getName())
                    .build();
            noticeListModels.add(noticeListModel);
        }
        return new ResponseEntity<>(noticeListModels, HttpStatus.OK);
    }

}
