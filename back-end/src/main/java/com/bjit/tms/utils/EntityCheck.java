package com.bjit.tms.utils;

import com.bjit.tms.entity.assignment_entities.AssignmentCreateEntity;
import com.bjit.tms.entity.assignment_entities.AssignmentSubmitEntity;
import com.bjit.tms.entity.batch_entities.BatchEntity;
import com.bjit.tms.entity.classroom_entities.ClassroomPostEntity;
import com.bjit.tms.entity.user_entities.TraineeEntity;
import com.bjit.tms.entity.user_entities.TrainerEntity;
import com.bjit.tms.repository.assignment_repositories.AssignmentCreateRepository;
import com.bjit.tms.repository.assignment_repositories.AssignmentSubmitRepository;
import com.bjit.tms.repository.batch_repositories.BatchRepository;
import com.bjit.tms.repository.classroom_repositories.ClassroomRepository;
import com.bjit.tms.repository.classroom_repositories.PostRepository;
import com.bjit.tms.repository.user_repositories.TraineeRepository;
import com.bjit.tms.repository.user_repositories.TrainerRepository;
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
    private final BatchRepository batchRepository;


    public Boolean checker(String type, Integer id){
        Boolean isNotAvailable = false;
        switch (type.toLowerCase()){
            case "trainer":
                Optional<TraineeEntity> optionalTrainee = traineeRepository.findById(id);
                if (optionalTrainee.isEmpty()){
                    isNotAvailable = true;
                }
                break;

            case "trainee":
                Optional <TrainerEntity> optionalTrainer = trainerRepository.findById(id);
                if (optionalTrainer.isEmpty()){
                    isNotAvailable = true;
                }
                break;
            case "assignmentcreate":
                Optional <AssignmentCreateEntity> optionalAssignmentCreate = assignmentCreateRepository.findById(id);
                if(optionalAssignmentCreate.isEmpty()){
                    isNotAvailable = true;
                }
                break;
            case "assignmentsubmit":
                Optional<AssignmentSubmitEntity> optionalAssignmentSubmit = assignmentSubmitRepository.findById(id);
                if (optionalAssignmentSubmit.isEmpty()){
                    isNotAvailable = true;
                }
                break;
            case "classroompost":
                Optional<ClassroomPostEntity> optionalClassroomPost = postRepository.findById(id);
                if (optionalClassroomPost.isEmpty()){
                    isNotAvailable = true;
                }
                break;
            case "batch":
                Optional<BatchEntity> optionalBatch = batchRepository.findById(id);
                if (optionalBatch.isEmpty()){
                    isNotAvailable = true;
                }
                break;
        }
        return isNotAvailable;
    }
}
