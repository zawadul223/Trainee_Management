package com.bjit.tms.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "user")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;
    private String username;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToOne(mappedBy = "user")
    private TraineeEntity traineeEntity;

    @OneToOne(mappedBy = "user")
    private TrainerEntity trainerEntity;

}
