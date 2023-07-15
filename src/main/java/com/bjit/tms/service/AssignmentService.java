package com.bjit.tms.service;

import com.bjit.tms.entity.AssignmentCreateEntity;
import com.bjit.tms.entity.AssignmentSubmitEntity;
import com.bjit.tms.model.AllSubmissions;
import com.bjit.tms.model.AssignmentCreateModel;
import com.bjit.tms.model.AssignmentList;
import com.bjit.tms.model.AssignmentSubmitModel;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AssignmentService {

    ResponseEntity<Object> createAssignment(Integer creatorId, AssignmentCreateModel assignmentCreateModel);
    ResponseEntity<Object> submitAssignment(Integer traineeId, AssignmentSubmitModel assignmentSubmitModel);
    public ResponseEntity<List<AssignmentCreateEntity>> assignments(Integer batchId);
    List<AllSubmissions> submissions(Integer assignmentId);
}