package com.bjit.tms.controller;

import com.bjit.tms.entity.AssignmentCreateEntity;
import com.bjit.tms.entity.AssignmentSubmitEntity;
import com.bjit.tms.model.AllSubmissions;
import com.bjit.tms.model.AssignmentCreateModel;
import com.bjit.tms.model.AssignmentList;
import com.bjit.tms.model.AssignmentSubmitModel;
import com.bjit.tms.service.AssignmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/assignment")
@RequiredArgsConstructor
public class AssignmentController {

    private final AssignmentService assignmentService;
    @PostMapping("/create/{trainerId}")
    public ResponseEntity<Object> assignmentCreate(@PathVariable Integer trainerId, @RequestBody AssignmentCreateModel assignmentCreateModel){
        return assignmentService.createAssignment(trainerId, assignmentCreateModel);
    }

    @GetMapping("/list/{batchId}")
    public ResponseEntity<List<AssignmentList>> assignmentList(@PathVariable Integer batchId){
        return assignmentService.assignments(batchId);
    }

    @PostMapping("/submit/{traineeId}")
    public ResponseEntity<Object> assignmentSubmit(@PathVariable Integer traineeId, @RequestBody AssignmentSubmitModel assignmentSubmitModel){
        return assignmentService.submitAssignment(traineeId, assignmentSubmitModel);
    }

    @GetMapping("/submission/{assignmentId}")
    public ResponseEntity<List<AllSubmissions>> submissionList(@PathVariable Integer assignmentId){
        return assignmentService.submissions(assignmentId);
    }
}
