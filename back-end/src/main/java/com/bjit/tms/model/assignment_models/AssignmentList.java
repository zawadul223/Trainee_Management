package com.bjit.tms.model.assignment_models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AssignmentList {

    private String courseName;
    private String message;
    private String file;
    private Date createdDate;
    private Date deadline;
    private String assignmentCreator;
}
