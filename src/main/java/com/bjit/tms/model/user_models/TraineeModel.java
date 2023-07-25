package com.bjit.tms.model.user_models;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TraineeModel {
    private Integer traineeId;
    private String name;
    private String traineePhoto;
    private String gender;
    private Date dateOfBirth;
    private String traineeEmail;
    private String traineeContactNo;
    private String degree;
    private Double cgpa;
    private Integer passingYear;
    private String address;
    private String password;
    @Column(name="batch_id")
    private Integer batchId;
}
