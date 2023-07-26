package com.bjit.tms.model.assignment_models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AssignmentSubmitModel {
    private Integer assignmentId;
    private String file;
    private Integer courseId;
}
