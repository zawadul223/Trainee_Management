package com.bjit.tms.service.implementation;

import com.bjit.tms.entity.Role;
import com.bjit.tms.entity.TraineeEntity;
import com.bjit.tms.entity.TrainerEntity;
import com.bjit.tms.entity.UserEntity;
import com.bjit.tms.model.*;
import com.bjit.tms.repository.TraineeRepository;
import com.bjit.tms.repository.TrainerRepository;
import com.bjit.tms.repository.UserRepository;
import com.bjit.tms.service.UserService;
import com.bjit.tms.utils.ImageUtils;
import com.bjit.tms.utils.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final TraineeRepository traineeRepository;
    private final TrainerRepository trainerRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final String directory = System.getProperty("user.dir");

    @Override
    public ResponseEntity<Object> traineeRegister(TraineeModel traineeModel){

        UserEntity userEntity = UserEntity.builder()
                .email(traineeModel.getTraineeEmail())
                .password(passwordEncoder.encode(traineeModel.getPassword()))
                .role(Role.TRAINEE)
                .build();
        TraineeEntity traineeEntity;
        userRepository.save(userEntity);
        try {
             traineeEntity = TraineeEntity.builder()
                    .name(traineeModel.getName())
                    .gender(traineeModel.getGender())
                    .dateOfBirth(traineeModel.getDateOfBirth())
                    .traineeEmail(traineeModel.getTraineeEmail())
                    .traineeContactNo(traineeModel.getTraineeContactNo())
                    .degree(traineeModel.getDegree())
                    .cgpa(traineeModel.getCgpa())
                    .passingYear(traineeModel.getPassingYear())
                    .address(traineeModel.getAddress())
                    .user(userRepository.findIdByEmail(traineeModel.getTraineeEmail()))
                    .build();

            traineeRepository.save(traineeEntity);
        }
        catch (Exception e){
            return new ResponseEntity<>("An error occurred", HttpStatus.BAD_REQUEST);
        }

        return ResponseEntity.status(HttpStatus.OK).body(Map.of("id",traineeEntity.getTraineeId()));
    }

    @Override
    public ResponseEntity<Object> trainerRegister(TrainerModel trainerModel){

        UserEntity userEntity = UserEntity.builder()
                .email(trainerModel.getTrainerEmail())
                .password(passwordEncoder.encode(trainerModel.getPassword()))
                .role(Role.TRAINER)
                .build();
        userRepository.save(userEntity);

        TrainerEntity trainerEntity = TrainerEntity.builder()
                .name(trainerModel.getName())
                .trainerEmail(trainerModel.getTrainerEmail())
                .trainerPhoto(trainerModel.getTrainerPhoto())
                .designation(trainerModel.getDesignation())
                .joiningDate(trainerModel.getJoiningDate())
                .experience(trainerModel.getExperience())
                .expertise(trainerModel.getExpertise())
                .trainerContactNo(trainerModel.getTrainerContactNo())
                .user(userRepository.findIdByEmail(trainerModel.getTrainerEmail()))
                .build();

        trainerRepository.save(trainerEntity);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<?> adminRegister(UserModel userModel) {

        UserEntity userEntity = UserEntity.builder()
                .email(userModel.getUsername())
                .password(passwordEncoder.encode(userModel.getPassword()))
                .role(Role.ADMIN)
                .build();
        userRepository.save(userEntity);
        return new ResponseEntity<>("Registered", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> uploadPhoto(MultipartFile file, String role, Integer id) {

        if(role.equalsIgnoreCase("trainee")){
            TraineeEntity traineeEntity = traineeRepository.findById(id).get();
            String filePath = directory+"\\main\\resources\\static\\TraineePhoto\\"+file.getOriginalFilename();

            traineeEntity.setTraineePhoto(file.getOriginalFilename());
            traineeEntity.setFileType(file.getContentType());
            traineeEntity.setFilePath(filePath);

            try {
                file.transferTo(new File(filePath));
            }
            catch (Exception e){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Cannot save image");
            }
            traineeRepository.save(traineeEntity);

        } else if (role.equalsIgnoreCase("trainer")) {
            TrainerEntity trainerEntity = trainerRepository.findById(id).get();
            String filePath = directory+"\\main\\resources\\static\\TrainerPhoto\\"+file.getOriginalFilename();
            trainerEntity.setTrainerPhoto(file.getOriginalFilename());
            trainerEntity.setFileType(file.getContentType());
            trainerEntity.setFilePath(filePath);
            try{
                file.transferTo(new File(filePath));
        }
            catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Couldn't save file");
        }
            trainerRepository.save(trainerEntity);
        }
        return ResponseEntity.status(HttpStatus.OK).body("Image Uploaded");
    }

    @Override
    public AuthenticationResponse login(AuthenticationRequest authenticationRequest){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.getEmail(),
                        authenticationRequest.getPassword()
                )
        );
        var user = userRepository.findByEmail(authenticationRequest.getEmail());
        Integer id = 0;
        if(user.getRole().toString().equals("TRAINEE")){
            TraineeEntity traineeEntity = traineeRepository.findByTraineeEmail(user.getEmail());
            id = traineeEntity.getTraineeId();
        }
        else if(user.getRole().toString().equals("TRAINER")){
            TrainerEntity trainerEntity = trainerRepository.findByTrainerEmail(user.getEmail());
            id = trainerEntity.getTrainerId();
        }
        else {
            id = user.getUserId();
        }
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .email(user.getEmail())
                .role(user.getRole().toString())
                .id(id)
                .build();
    }

    @Override
    public UserEntity findByEmail(String email){
        return userRepository.findByEmail(email);
    }
}
