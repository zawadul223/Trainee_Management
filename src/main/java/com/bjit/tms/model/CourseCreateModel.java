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
public class CourseCreateModel {
    private String courseName;
    private Date startDate;
    private Date endDate;
}
