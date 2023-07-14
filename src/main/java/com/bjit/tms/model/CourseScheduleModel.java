package com.bjit.tms.model;

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

    private Date startDate;
    private Date endDate;
    private Integer trainerId;
}
