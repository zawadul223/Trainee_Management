package com.bjit.tms.service.implementation;

import com.bjit.tms.entity.classroom_entities.ClassroomEntity;
import com.bjit.tms.entity.classroom_entities.ClassroomPostEntity;
import com.bjit.tms.entity.classroom_entities.CommentEntity;
import com.bjit.tms.entity.user_entities.TraineeEntity;
import com.bjit.tms.entity.user_entities.TrainerEntity;
import com.bjit.tms.model.classroom_models.CommentListModel;
import com.bjit.tms.model.classroom_models.CommentModel;
import com.bjit.tms.model.classroom_models.PostListModel;
import com.bjit.tms.model.classroom_models.PostModel;
import com.bjit.tms.repository.classroom_repositories.ClassroomRepository;
import com.bjit.tms.repository.classroom_repositories.CommentRepository;
import com.bjit.tms.repository.classroom_repositories.PostRepository;
import com.bjit.tms.repository.user_repositories.TraineeRepository;
import com.bjit.tms.repository.user_repositories.TrainerRepository;
import com.bjit.tms.service.ClassroomService;
import com.bjit.tms.utils.EntityCheck;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
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
    private final EntityCheck entityCheck;
    private final String folderPath = "E:\\Projects\\Final\\Files\\Post\\";
    @Override
    public ResponseEntity<Object> createPost(Integer classroomId, Integer trainerId, PostModel postModel) {

        Optional<TrainerEntity> optionalTrainer = trainerRepository.findById(trainerId);
        if (optionalTrainer.isEmpty()) {
            return new ResponseEntity<>("Trainer not found", HttpStatus.NOT_FOUND);
        }
        TrainerEntity trainerEntity = optionalTrainer.get();
//        if (entityCheck.checker("trainer", trainerId)){
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//        }
//
//        TrainerEntity trainerEntity = trainerRepository.findById(trainerId).get();

        Optional<ClassroomEntity> optionalClassroom = classroomRepository.findById(classroomId);
        if (optionalClassroom.isEmpty()) {
            return new ResponseEntity<>("Classroom not found", HttpStatus.NOT_FOUND);
        }
        ClassroomEntity classroomEntity = optionalClassroom.get();

        ClassroomPostEntity classroomPostEntity = ClassroomPostEntity.builder()
                .message(postModel.getMessage())
                .date(Date.valueOf(LocalDate.now()))
                .build();

        classroomPostEntity.setTrainerEntity(trainerEntity);
        classroomPostEntity.setClassroomEntity(classroomEntity);
        postRepository.save(classroomPostEntity);
        return new ResponseEntity<>("Post Created", HttpStatus.OK);
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
            PostListModel p = PostListModel.builder()
                    .trainerName(i.getTrainerEntity().getName())
                    //.file(i.getFile())
                    .message(i.getMessage())
                    .date(i.getDate())
                    .build();
            postListModels.add(p);
        }
        return new ResponseEntity<>(postListModels, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> createComment(Integer traineeId, CommentModel commentModel) {

//        if (entityCheck.checker("trainee", traineeId)){
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//        }
//
//        TraineeEntity traineeEntity = traineeRepository.findById(traineeId).get();
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

    @Override
    public ResponseEntity<?> postFile(MultipartFile file, Integer postId) {
        Optional<ClassroomPostEntity> optionalClassroomPost = postRepository.findById(postId);

        if(optionalClassroomPost.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        ClassroomPostEntity classroomPostEntity = optionalClassroomPost.get();
 //       Optional<ClassroomPostEntity> optionalClassroomPost = postRepository.findById(postId);
//        if (entityCheck.checker("classroom", postId)){
//            return ResponseEntity.notFound().build();
//        }
//        ClassroomPostEntity classroomPostEntity = postRepository.findById(postId).get();
        String filePath = "D:\\Final Project\\tms\\back-end\\src\\main\\resources\\static\\ClassroomPostFile\\" + file.getOriginalFilename();
        //String filePath = folderPath +"ClassroomPostFile\\"+ file.getOriginalFilename();
        classroomPostEntity.setFile(file.getOriginalFilename());
        classroomPostEntity.setFilePath(filePath);
        classroomPostEntity.setFileType(file.getContentType());

        try{
            file.transferTo(new File(filePath));
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body("Couldn't save file");
        }
        postRepository.save(classroomPostEntity);
        return new ResponseEntity<>("Post uploaded", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getFile(Integer postId) {
        if (entityCheck.checker("classroompost", postId)) {
            return ResponseEntity.notFound().build();
        }
        ClassroomPostEntity classroomPostEntity = postRepository.findById(postId).get();
        String filePath = classroomPostEntity.getFilePath();
        File file = new File(filePath);
//        byte[] file;
//        try {
//            file = Files.readAllBytes(new File(filePath).toPath());
//        } catch (Exception e) {
//            return ResponseEntity.notFound().build();
//        }
        try{
            InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file.getName());
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .headers(headers)
                    .contentType(MediaType.APPLICATION_PDF)
                    .contentLength(file.length())
                    .body(resource);
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body("Error with file");
        }

    }

}
