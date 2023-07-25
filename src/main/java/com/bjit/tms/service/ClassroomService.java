package com.bjit.tms.service;

import com.bjit.tms.model.classroom_models.CommentListModel;
import com.bjit.tms.model.classroom_models.CommentModel;
import com.bjit.tms.model.classroom_models.PostListModel;
import com.bjit.tms.model.classroom_models.PostModel;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public interface ClassroomService {

    ResponseEntity<Object> createPost(Integer classroomId, Integer trainerId, PostModel postModel);
    ResponseEntity<List<PostListModel>> postList(Integer classroomId);
    ResponseEntity<Object> createComment(Integer traineeId, CommentModel commentModel);
    ResponseEntity<List<CommentListModel>> commentList(Integer postId);
    ResponseEntity<?> postFile(MultipartFile file, Integer classroomId);
    ResponseEntity<?> getFile(Integer postId);
}
