package com.bjit.tms.controller;

import com.bjit.tms.model.user_models.AuthenticationRequest;
import com.bjit.tms.model.user_models.TraineeModel;
import com.bjit.tms.model.user_models.TrainerModel;
import com.bjit.tms.model.user_models.UserModel;
import com.bjit.tms.repository.user_repositories.UserRepository;
import com.bjit.tms.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserRepository userRepository;
    private final UserService userService;

    @PostMapping("/register/trainee")
    public ResponseEntity<Object> traineeRegisterURL(@RequestBody TraineeModel traineeModel){
        return userService.traineeRegister(traineeModel);
    }

    @PostMapping("/register/admin")
    public ResponseEntity<?> adminRegisterURL(@RequestBody UserModel usermodel){
        return userService.adminRegister(usermodel);
    }

    @PostMapping("/register/trainer")
    public ResponseEntity<Object> trainerRegisterURL(@RequestBody TrainerModel trainerModel){
        return userService.trainerRegister(trainerModel);
    }

    @PostMapping("/photo/trainee/{id}")
    public ResponseEntity<?> photoUploadTrainee(@PathVariable Integer id, @RequestParam("image") MultipartFile multipartFile){
        return userService.uploadPhoto(multipartFile, "trainee", id);
    }

    @PostMapping("/photo/trainer/{id}")
    public ResponseEntity<?> photoUploadTrainer(@PathVariable Integer id, @RequestParam("image") MultipartFile multipartFile){
        return userService.uploadPhoto(multipartFile, "trainer", id);
    }

    @GetMapping("/unassigned/trainees")
    public ResponseEntity<?> unassignedTraineeList(){
        return userService.unassignedTrainees();
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody AuthenticationRequest authenticationRequest){
        return new ResponseEntity<>(userService.login(authenticationRequest), HttpStatus.OK);
        //return new ResponseEntity<>("Hello", HttpStatus.OK);
    }

    @GetMapping("/trainee/getBatch/{traineeId}")
    public ResponseEntity<?> getBatchOfTrainee(@PathVariable Integer traineeId){
        return userService.traineeBatch(traineeId);
    }

    @GetMapping("/trainer/getBatch/{trainerId}")
    public ResponseEntity<?> getBatchesOfTrainer(@PathVariable Integer trainerId){
        return userService.getBatchByTrainerId(trainerId);
    }


}
