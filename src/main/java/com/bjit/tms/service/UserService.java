package com.bjit.tms.service;

import com.bjit.tms.entity.UserEntity;
import com.bjit.tms.model.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface UserService {

    ResponseEntity<Object> traineeRegister(TraineeModel traineeModel);
    ResponseEntity<Object> trainerRegister(TrainerModel trainerModel);
    ResponseEntity<?> adminRegister(UserModel userModel);
    ResponseEntity<?> uploadPhoto(MultipartFile file, String role, Integer id);
    AuthenticationResponse login(AuthenticationRequest authenticationRequest);
    UserEntity findByEmail(String email);


}
