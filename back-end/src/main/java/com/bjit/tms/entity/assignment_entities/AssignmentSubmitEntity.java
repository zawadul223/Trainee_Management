package com.bjit.tms.entity.assignment_entities;

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
@Table(name="assignment_submit")
public class AssignmentSubmitEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer submissionId;
    private String file;
   // private Integer traineeId;
    private Date submissionDate;
    private String traineeName;
    private String pathname;
    private String fileType;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "assignmentId")
    private AssignmentCreateEntity assignmentCreateEntity;

//    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//    @JoinColumn(name = "traineeId")
//    private TraineeEntity traineeEntity;
}
