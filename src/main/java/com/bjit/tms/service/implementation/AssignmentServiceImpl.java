package com.bjit.tms.service.implementation;

import com.bjit.tms.entity.AssignmentCreateEntity;
import com.bjit.tms.entity.BatchEntity;
import com.bjit.tms.entity.CourseEntity;
import com.bjit.tms.model.AssignmentCreateModel;
import com.bjit.tms.repository.AssignmentCreateRepository;
import com.bjit.tms.repository.CourseRepository;
import com.bjit.tms.service.AssignmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AssignmentServiceImpl implements AssignmentService {
    private final AssignmentCreateRepository assignmentCreateRepository;
    private final CourseRepository courseRepository;
    @Override
    public ResponseEntity<Object> createAssignment(Integer courseId, AssignmentCreateModel assignmentCreateModel) {

        Optional<CourseEntity> optionalCourse = courseRepository.findById(courseId);
        if (optionalCourse.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        CourseEntity courseEntity = optionalCourse.get();

        AssignmentCreateEntity assignmentCreateEntity = AssignmentCreateEntity.builder()
                .file(assignmentCreateModel.getFile())
                .deadline(assignmentCreateModel.getDeadline())
                .message(assignmentCreateModel.getMessage())
                .courseId(courseId)
                .build();

        assignmentCreateRepository.save(assignmentCreateEntity);

        List<AssignmentCreateEntity> assignmentList = new ArrayList<AssignmentCreateEntity>();
        assignmentList.add(assignmentCreateEntity);

        courseEntity.setAssignmentCreateEntities(assignmentList);
        return new ResponseEntity<>("Assignment created",HttpStatus.CREATED);
    }


}
