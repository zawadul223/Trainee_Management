package com.bjit.tms.service.implementation;

import com.bjit.tms.entity.BatchEntity;
import com.bjit.tms.entity.CourseEntity;
import com.bjit.tms.model.CourseCreateModel;
import com.bjit.tms.repository.BatchRepository;
import com.bjit.tms.repository.CourseRepository;
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

    @Override
    public ResponseEntity<Object> createCourse(CourseCreateModel courseCreateModel) {
        CourseEntity courseEntity = CourseEntity.builder()
                .courseName(courseCreateModel.getCourseName())
                .startDate(courseCreateModel.getStartDate())
                .endDate(courseCreateModel.getEndDate())
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
        return null;
    }
}
