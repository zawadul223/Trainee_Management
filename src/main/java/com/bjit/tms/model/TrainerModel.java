package com.bjit.tms.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TrainerModel {
    private Integer trainerId;
    private String name;
    private String trainerPhoto;
    private String designation;
    private Date joiningDate;
    private Double experience;
    private String expertise;
    private String trainerContactNo;
}
