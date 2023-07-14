package com.bjit.tms.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AssignmentCreateModel {
    private String file;
    private String message;
    private Date deadline;
    private Integer courseId;
}
