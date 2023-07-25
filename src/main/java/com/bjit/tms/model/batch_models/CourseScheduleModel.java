package com.bjit.tms.model.batch_models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CourseScheduleModel {

    private Integer courseId;
    private Date startDate;
    private Date endDate;
    private Integer trainerId;
}
