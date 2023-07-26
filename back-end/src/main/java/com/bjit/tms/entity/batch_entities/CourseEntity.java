package com.bjit.tms.entity.batch_entities;

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
@Table(name="course")
public class CourseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer courseId;
    private String courseName;
    //private Integer scheduleId;
//    private Date startDate;
//    private Date endDate;

//    @JsonIgnore
//    @OneToMany(cascade = CascadeType.ALL)
//    @JoinTable(
//            name = "course_assignment",
//            joinColumns = @JoinColumn(
//                    name="courseId"
//            ),
//            inverseJoinColumns = @JoinColumn(
//                    name = "assignmentId"
//            )
//    )
//    private List<AssignmentCreateEntity> assignmentCreateEntities;

}
