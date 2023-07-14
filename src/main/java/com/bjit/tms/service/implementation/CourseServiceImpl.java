package com.bjit.tms.service.implementation;

import com.bjit.tms.entity.*;
import com.bjit.tms.model.CourseCreateModel;
import com.bjit.tms.repository.*;
import com.bjit.tms.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final BatchRepository batchRepository;
    private final TrainerRepository trainerRepository;
    private final CourseSchduleRepository courseSchduleRepository;

    @Override
    public ResponseEntity<Object> createCourse(CourseCreateModel courseCreateModel) {

        Integer trainerId = courseCreateModel.getTrainerId();
        Optional<TrainerEntity> optionalTrainer = trainerRepository.findById(trainerId);
        if (optionalTrainer.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        CourseScheduleEntity courseScheduleEntity = CourseScheduleEntity.builder()
                .startDate(courseCreateModel.getStartDate())
                .endDate(courseCreateModel.getEndDate())
                .trainerId(trainerId)
                .build();
        courseSchduleRepository.save(courseScheduleEntity);

        CourseEntity courseEntity = CourseEntity.builder()
                .courseName(courseCreateModel.getCourseName())
                .scheduleId(courseSchduleRepository.findScheduleIdByTrainerId(trainerId))
                .build();

        courseRepository.save(courseEntity);

        return new ResponseEntity<>(courseEntity, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> assignCoursetoBatch(Map<Integer, List<Integer>> batchCourseMap) {
        for (Map.Entry<Integer, List<Integer>> entry : batchCourseMap.entrySet()) {
            Integer batchId = entry.getKey();
            List<Integer> courseIds = entry.getValue();

            Optional<BatchEntity> batchOptional = batchRepository.findById(batchId);
            if (batchOptional.isPresent()) {
                BatchEntity batch = batchOptional.get();

                List<CourseEntity> courses = courseRepository.findAllById(courseIds);
                batch.setCourseEntityList(courses);

                batchRepository.save(batch);

            } else {
                // Handle case when batch with the given ID is not found
                return new ResponseEntity<>("Batch with ID " + batchId + " not found.", HttpStatus.NOT_FOUND);
            }

        }
        return new ResponseEntity<>("Courses have been assigned", HttpStatus.CREATED);

    }

    @Override
    public ResponseEntity<Object> assignCoursetoTrainer(Integer trainerId, List<Integer> courseId) {
        Optional<TrainerEntity> optionalTrainer = trainerRepository.findById(trainerId);
        if (optionalTrainer.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        TrainerEntity trainerEntity = optionalTrainer.get();

        List<CourseEntity> courseEntityList = courseRepository.findAllById(courseId);
        trainerEntity.setCourseEntityList(courseEntityList);
        trainerRepository.save(trainerEntity);
        return new ResponseEntity<>("Assigned courses to trainer successfully", HttpStatus.OK);
    }
}
