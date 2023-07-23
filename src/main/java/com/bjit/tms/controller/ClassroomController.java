package com.bjit.tms.controller;

import com.bjit.tms.model.CommentListModel;
import com.bjit.tms.model.CommentModel;
import com.bjit.tms.model.PostListModel;
import com.bjit.tms.model.PostModel;
import com.bjit.tms.service.ClassroomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/classroom")
@RequiredArgsConstructor
public class ClassroomController {

    private final ClassroomService classroomService;
    @PostMapping("/post/{trainerId}")
    public ResponseEntity<Object> post(@PathVariable Integer trainerId, @RequestBody PostModel postModel){
        return classroomService.createPost(trainerId, postModel);
    }

    @GetMapping("/allPosts/{classroomId}")
    public ResponseEntity<List<PostListModel>> allPosts(@PathVariable Integer classroomId){
        return classroomService.postList(classroomId);
    }

    @PostMapping("/comment/{traineeId}")
    public ResponseEntity<Object> comment(@PathVariable Integer traineeId, @RequestBody CommentModel commentModel){
        return classroomService.createComment(traineeId, commentModel);
    }

    @GetMapping("/allComments/{postId}")
    public ResponseEntity<List<CommentListModel>> allComments(@PathVariable Integer postId){
        return classroomService.commentList(postId);
    }

    @PostMapping("/post/file/{postId}")
    public ResponseEntity<?> postUpload(@RequestParam("file") MultipartFile file, @PathVariable Integer postId){
        return classroomService.postFile(file, postId);
    }

    @GetMapping("/post/get/{postId}")
    public ResponseEntity<?> postDownload(@PathVariable Integer postId){
        return classroomService.getFile(postId);
    }
}
