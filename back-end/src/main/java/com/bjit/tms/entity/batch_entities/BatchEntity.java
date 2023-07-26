package com.bjit.tms.entity.batch_entities;

import com.bjit.tms.entity.classroom_entities.ClassroomEntity;
import com.bjit.tms.entity.user_entities.TraineeEntity;
import com.bjit.tms.entity.user_entities.TrainerEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL)
    private List<TraineeEntity> traineeEntityList;

    @JsonIgnore
    @OneToOne(mappedBy = "batchEntity",cascade = CascadeType.ALL)
    private ClassroomEntity classroomEntity;

    @JsonIgnore
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "course_batch",
            joinColumns = @JoinColumn(
                    name="courseId"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "batchId"
            )
    )
    private List<CourseEntity> courseEntityList;

        @JsonIgnore
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "batch_trainer",
            joinColumns = @JoinColumn(
                    name="batchId"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "trainerId"
            )
    )
    private List<TrainerEntity> trainerEntityList;
}
