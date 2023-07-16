package com.bjit.tms.service.implementation;

import com.bjit.tms.entity.*;
import com.bjit.tms.model.CommentListModel;
import com.bjit.tms.model.CommentModel;
import com.bjit.tms.model.PostListModel;
import com.bjit.tms.model.PostModel;
import com.bjit.tms.repository.*;
import com.bjit.tms.service.ClassroomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClassroomServiceImpl implements ClassroomService {
    private final ClassroomRepository classroomRepository;
    private final PostRepository postRepository;
    private final TrainerRepository trainerRepository;
    private final TraineeRepository traineeRepository;
    private final CommentRepository commentRepository;
    @Override
    public ResponseEntity<Object> createPost(Integer trainerId, PostModel postModel) {

        Optional<TrainerEntity> optionalTrainer = trainerRepository.findById(trainerId);
        if (optionalTrainer.isEmpty()) {
            return new ResponseEntity<>("Trainer not found", HttpStatus.NOT_FOUND);
        }
        TrainerEntity trainerEntity = optionalTrainer.get();

        Integer classroomId = postModel.getClassroomId();
        Optional<ClassroomEntity> optionalClassroom = classroomRepository.findById(classroomId);
        if (optionalClassroom.isEmpty()) {
            return new ResponseEntity<>("Classroom not found", HttpStatus.NOT_FOUND);
        }
        ClassroomEntity classroomEntity = optionalClassroom.get();

        ClassroomPostEntity classroomPostEntity = ClassroomPostEntity.builder()
                .file(postModel.getFile())
                .message(postModel.getMessage())
                .date(Date.valueOf(LocalDate.now()))
                .build();

        classroomPostEntity.setTrainerEntity(trainerEntity);
        classroomPostEntity.setClassroomEntity(classroomEntity);
        postRepository.save(classroomPostEntity);
        return new ResponseEntity<>("Post Created", HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<List<PostListModel>> postList(Integer classroomId) {
        Optional<ClassroomEntity> optionalClassroom = classroomRepository.findById(classroomId);
        if (optionalClassroom.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        ClassroomEntity classroomEntity = optionalClassroom.get();
        List<ClassroomPostEntity> posts = postRepository.findByClassroomEntity(classroomEntity);
        List<PostListModel> postListModels = new ArrayList<PostListModel>();
        for(ClassroomPostEntity i : posts){
            PostListModel  p = PostListModel.builder()
                    .trainerName(i.getTrainerEntity().getName())
                    .file(i.getFile())
                    .message(i.getMessage())
                    .date(i.getDate())
                    .build();
            postListModels.add(p);
        }
        return new ResponseEntity<>(postListModels, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> createComment(Integer traineeId, CommentModel commentModel) {

        Optional<TraineeEntity> optionalTrainee = traineeRepository.findById(traineeId);
        if (optionalTrainee.isEmpty()) {
            return new ResponseEntity<>("Trainee not found", HttpStatus.NOT_FOUND);
        }
        TraineeEntity traineeEntity = optionalTrainee.get();

        Integer postId = commentModel.getPostId();
        Optional<ClassroomPostEntity> optionalPost = postRepository.findById(postId);
        if (optionalPost.isEmpty()) {
            return new ResponseEntity<>("Post not found", HttpStatus.NOT_FOUND);
        }
        ClassroomPostEntity classroomPostEntity = optionalPost.get();
        CommentEntity commentEntity = CommentEntity.builder()
                .comment(commentModel.getComment())
                .date(Date.valueOf(LocalDate.now()))
                .build();

        commentEntity.setClassroomPostEntity(classroomPostEntity);
        commentEntity.setTraineeEntity(traineeEntity);
        commentRepository.save(commentEntity);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<CommentListModel>> commentList(Integer postId) {

        Optional<ClassroomPostEntity> optionalPost = postRepository.findById(postId);
        if (optionalPost.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        ClassroomPostEntity classroomPostEntity = optionalPost.get();
        List<CommentEntity> commentEntities = commentRepository.findCommentEntitiesByClassroomPostEntity(classroomPostEntity);
        List<CommentListModel> commentListModelList = new ArrayList<CommentListModel>();
        for (CommentEntity c : commentEntities) {
            CommentListModel commentListModel = CommentListModel.builder()
                    .name(c.getTraineeEntity().getName())
                    .comment(c.getComment())
                    .date(c.getDate())
                    .build();
            commentListModelList.add(commentListModel);
        }
        return new ResponseEntity<>(commentListModelList, HttpStatus.OK);
    }


}
