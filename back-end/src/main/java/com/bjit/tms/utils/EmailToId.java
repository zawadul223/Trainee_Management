package com.bjit.tms.utils;

import com.bjit.tms.entity.user_entities.Role;
import com.bjit.tms.entity.user_entities.TraineeEntity;
import com.bjit.tms.entity.user_entities.TrainerEntity;
import com.bjit.tms.entity.user_entities.UserEntity;
import com.bjit.tms.repository.user_repositories.TraineeRepository;
import com.bjit.tms.repository.user_repositories.TrainerRepository;
import com.bjit.tms.repository.user_repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class EmailToId {
    private final TraineeRepository traineeRepository;
    private final TrainerRepository trainerRepository;
    private final UserRepository userRepository;

    public ResponseEntity<?> id(String username){
        UserEntity userEntity = userRepository.findByEmail(username);

        if (userEntity.getRole() == Role.TRAINEE){
            TraineeEntity traineeEntity = traineeRepository.findByTraineeEmail(username);
            return ResponseEntity.status(HttpStatus.OK).body(Map.of("id",traineeEntity.getTraineeId()));
        }
        else if (userEntity.getRole() == Role.TRAINER) {
            TrainerEntity trainerEntity = trainerRepository.findByTrainerEmail(username);
            return ResponseEntity.status(HttpStatus.OK).body(Map.of("id",trainerEntity.getTrainerId()));
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
    }
}
