package com.bjit.tms.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name="batch")
public class BatchEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer batchId;
    private String batchName;
    private Date startDate;
    private Date endDate;

    @OneToMany(cascade = CascadeType.ALL)
    private List<TraineeEntity> traineeEntityList;

    @ManyToMany(cascade = CascadeType.ALL)
    private List<TrainerEntity> trainerEntityList;
}
