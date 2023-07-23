package com.bjit.tms.service;

import com.bjit.tms.entity.AssignmentCreateEntity;
import com.bjit.tms.entity.AssignmentSubmitEntity;
import com.bjit.tms.model.AllSubmissions;
import com.bjit.tms.model.AssignmentCreateModel;
import com.bjit.tms.model.AssignmentList;
import com.bjit.tms.model.AssignmentSubmitModel;
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
