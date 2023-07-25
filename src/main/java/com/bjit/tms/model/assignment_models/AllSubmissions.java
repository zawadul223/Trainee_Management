package com.bjit.tms.model.assignment_models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AllSubmissions {
    private String fileName;
    private Date submissionDate;
    private String traineeName;
    private Integer assignmentId;
}
