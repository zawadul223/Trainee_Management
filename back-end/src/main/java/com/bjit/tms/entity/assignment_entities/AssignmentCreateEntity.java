package com.bjit.tms.entity.assignment_entities;

import com.bjit.tms.entity.batch_entities.BatchEntity;
import com.bjit.tms.entity.batch_entities.CourseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name="assignment_create")
public class AssignmentCreateEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer assignmentId;
    private String message;
    private String file;
    private Date createdDate;
    private Date deadline;
    private String assignmentCreator;

    @Column(name = "assignmentCreateFile")
    private String filePath;

    //@JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "courseId")
    private CourseEntity courseEntity;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "batchId")
    private BatchEntity batchEntity;

}
