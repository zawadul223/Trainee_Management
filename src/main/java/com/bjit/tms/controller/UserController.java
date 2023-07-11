package com.bjit.tms.controller;

import com.bjit.tms.model.AuthenticationRequest;
import com.bjit.tms.model.TraineeModel;
import com.bjit.tms.model.TrainerModel;
import com.bjit.tms.model.UserModel;
import com.bjit.tms.repository.UserRepository;
import com.bjit.tms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    private UserRepository userRepository;
    @Autowired
    private UserService userService;

    @PostMapping("/register/trainee")
    public ResponseEntity<Object> traineeRegisterURL(@RequestBody TraineeModel traineeModel){
        return userService.traineeRegister(traineeModel);
    }

    @PostMapping("/register/trainer")
    public ResponseEntity<Object> trainerRegisterURL(@RequestBody TrainerModel trainerModel){
        return userService.trainerRegister(trainerModel);
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody AuthenticationRequest authenticationRequest){
        return new ResponseEntity<>(userService.login(authenticationRequest), HttpStatus.OK);
        //return new ResponseEntity<>("Hello", HttpStatus.OK);
    }
}
