package com.bjit.tms.entity.user_entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

//import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "trainee")
public class TraineeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer traineeId;
    private String name;
    private String traineePhoto;
    private String fileType;
    private String gender;
    private Date dateOfBirth;
    private String traineeEmail;
    private String traineeContactNo;
    private String degree;
    private Double cgpa;
    private Integer passingYear;
    private String address;
    private String filePath;
    private boolean assignedBatch;

    @OneToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
}
