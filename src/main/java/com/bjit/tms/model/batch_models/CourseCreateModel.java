package com.bjit.tms.model.batch_models;

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
//    private Date startDate;
//    private Date endDate;
    //private Integer trainerId;
}
