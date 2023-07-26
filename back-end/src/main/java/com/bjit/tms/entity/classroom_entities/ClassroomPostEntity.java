package com.bjit.tms.entity.classroom_entities;

import com.bjit.tms.entity.user_entities.TrainerEntity;
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
@Table(name="classroom_post")
public class ClassroomPostEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer postId;
    private String message;
    private String file;
    private Date date;
    private String filePath;
    private String fileType;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "classroomId")
    private ClassroomEntity classroomEntity;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "trainerId")
    private TrainerEntity trainerEntity;
}
