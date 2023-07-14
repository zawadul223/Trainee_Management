package com.bjit.tms.service;

import com.bjit.tms.model.AssignmentCreateModel;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface AssignmentService {

    ResponseEntity<Object> createAssignment(Integer courseId, AssignmentCreateModel assignmentCreateModel);
}
