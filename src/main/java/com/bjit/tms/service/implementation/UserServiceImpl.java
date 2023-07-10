package com.bjit.tms.service.implementation;

import com.bjit.tms.entity.TraineeEntity;
import com.bjit.tms.entity.TrainerEntity;
import com.bjit.tms.entity.UserEntity;
import com.bjit.tms.model.AuthenticationRequest;
import com.bjit.tms.model.AuthenticationResponse;
import com.bjit.tms.model.TraineeModel;
import com.bjit.tms.model.TrainerModel;
import com.bjit.tms.repository.TraineeRepository;
import com.bjit.tms.repository.TrainerRepository;
import com.bjit.tms.repository.UserRepository;
import com.bjit.tms.service.UserService;
import com.bjit.tms.utils.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final TraineeRepository traineeRepository;
    private final TrainerRepository trainerRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public ResponseEntity<Object> traineeRegister(TraineeModel traineeModel){
        TraineeEntity traineeEntity = TraineeEntity.builder()
                .name(traineeModel.getName())
                .traineePhoto(traineeModel.getTraineePhoto())
                .gender(traineeModel.getGender())
                .dateOfBirth(traineeModel.getDateOfBirth())
                .traineeEmail(traineeModel.getEmail())
                .traineeContactNo(traineeModel.getTraineeContactNo())
                .degree(traineeModel.getDegree())
                .cgpa(traineeModel.getCgpa())
                .passingYear(traineeModel.getPassingYear())
                .address(traineeModel.getAddress())
                .build();

        traineeRepository.save(traineeEntity);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Object> trainerRegister(TrainerModel trainerModel){
        TrainerEntity trainerEntity = TrainerEntity.builder()
                .name(trainerModel.getName())
                .trainerPhoto(trainerModel.getTrainerPhoto())
                .designation(trainerModel.getDesignation())
                .joiningDate(trainerModel.getJoiningDate())
                .experience(trainerModel.getExperience())
                .expertise(trainerModel.getExpertise())
                .trainerContactNo(trainerModel.getTrainerContactNo())
                .build();

        trainerRepository.save(trainerEntity);

        return new ResponseEntity<>(HttpStatus.CREATED);
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
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    @Override
    public UserEntity findByEmail(String email){
        return userRepository.findByEmail(email);
    }
}
