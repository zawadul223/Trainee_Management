package com.bjit.tms.controller;

import com.bjit.tms.model.TraineeModel;
import com.bjit.tms.model.TrainerModel;
import com.bjit.tms.model.UserModel;
import com.bjit.tms.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    private UserRepository userRepository;

    @PostMapping("/register/trainee")
    public ResponseEntity<Object> traineeRegister(@RequestBody TraineeModel traineeModel){
        return null;
    }

    @PostMapping("/register/trainer")
    public ResponseEntity<Object> trainerRegister(@RequestBody TrainerModel trainerModel){
        return null;
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody UserModel userModel){
        return null;
    }
}
