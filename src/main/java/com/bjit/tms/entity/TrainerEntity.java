package com.bjit.tms.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "trainer")
public class TrainerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer trainerId;
    private String name;
    private String trainerPhoto;
    private String designation;
    private Date joiningDate;
    private Double experience;
    private String expertise;
    private String trainerContactNo;

    @OneToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
}
