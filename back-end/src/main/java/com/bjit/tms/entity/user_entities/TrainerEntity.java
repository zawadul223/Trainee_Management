package com.bjit.tms.entity.user_entities;

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
    private String trainerEmail;
    private String trainerPhoto;
    private String fileType;
    private String designation;
    private Date joiningDate;
    private Double experience;
    private String expertise;
    private String trainerContactNo;
    private String filePath;

    @OneToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

//    @ManyToMany(cascade = CascadeType.ALL)
//    @JoinTable(
//            name = "course_trainer",
//            joinColumns = @JoinColumn(
//                    name="trainer_Id"
//            ),
//            inverseJoinColumns = @JoinColumn(
//                    name = "course_id"
//            )
//    )
//    private List<CourseEntity> courseEntityList;

}
