package com.bjit.tms.utils;

import com.bjit.tms.entity.*;
import com.bjit.tms.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EntityCheck {
    private final TraineeRepository traineeRepository;
    private final TrainerRepository trainerRepository;
    private final AssignmentCreateRepository assignmentCreateRepository;
    private final AssignmentSubmitRepository assignmentSubmitRepository;
    private final ClassroomRepository classroomRepository;
    private final PostRepository postRepository;


    public Boolean checker(String type, Integer id){
        Boolean isAvailable = false;
        switch (type.toLowerCase()){
            case "trainer":
                Optional<TraineeEntity> optionalTrainee = traineeRepository.findById(id);
                if (optionalTrainee.isEmpty()){
                    isAvailable = true;
                }
                break;

            case "trainee":
                Optional <TrainerEntity> optionalTrainer = trainerRepository.findById(id);
                if (optionalTrainer.isEmpty()){
                    isAvailable = true;
                }
                break;
            case "assignmentcreate":
                Optional <AssignmentCreateEntity> optionalAssignmentCreate = assignmentCreateRepository.findById(id);
                if(optionalAssignmentCreate.isEmpty()){
                    isAvailable = true;
                }
                break;
            case "assignmentsubmit":
                Optional<AssignmentSubmitEntity> optionalAssignmentSubmit = assignmentSubmitRepository.findById(id);
                if (optionalAssignmentSubmit.isEmpty()){
                    isAvailable = true;
                }
                break;
            case "classroompost":
                Optional<ClassroomPostEntity> optionalClassroomPost = postRepository.findById(id);
                if (optionalClassroomPost.isEmpty()){
                    isAvailable = true;
                }
                break;
        }
        return isAvailable;
    }
}
