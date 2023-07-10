package com.bjit.tms.service;

import com.bjit.tms.entity.UserEntity;
import com.bjit.tms.model.AuthenticationRequest;
import com.bjit.tms.model.AuthenticationResponse;
import com.bjit.tms.model.TraineeModel;
import com.bjit.tms.model.TrainerModel;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    ResponseEntity<Object> traineeRegister();

    ResponseEntity<Object> traineeRegister(TraineeModel traineeModel);

    ResponseEntity<Object> trainerRegister();
    AuthenticationResponse login(AuthenticationRequest authenticationRequest);
    UserEntity findByEmail(String email);

    ResponseEntity<Object> trainerRegister(TrainerModel trainerModel);
}
