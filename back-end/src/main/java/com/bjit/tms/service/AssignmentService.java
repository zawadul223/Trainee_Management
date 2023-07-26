package com.bjit.tms.service;

import com.bjit.tms.model.assignment_models.AllSubmissions;
import com.bjit.tms.model.assignment_models.AssignmentCreateModel;
import com.bjit.tms.model.assignment_models.AssignmentList;
import com.bjit.tms.model.assignment_models.AssignmentSubmitModel;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public interface AssignmentService {

    ResponseEntity<Object> createAssignment(Integer creatorId, AssignmentCreateModel assignmentCreateModel);
    ResponseEntity<Object> submitAssignment(Integer traineeId, AssignmentSubmitModel assignmentSubmitModel);
    ResponseEntity<List<AssignmentList>> assignments(Integer batchId);
    ResponseEntity<List<AllSubmissions>> submissions(Integer assignmentId);
    ResponseEntity<?> assignmentCreateFile(MultipartFile file, Integer assignmentCreateId);
    ResponseEntity<?> assignmentSubmitFile(MultipartFile file, Integer assignmentSubmitId);
}
