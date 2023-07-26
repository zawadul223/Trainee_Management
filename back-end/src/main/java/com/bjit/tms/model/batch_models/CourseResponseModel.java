package com.bjit.tms.model.batch_models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseResponseModel {
    private String courseName;
    private Date startDate;
    private Date endDate;
    private List<String> trainerNames;
}
